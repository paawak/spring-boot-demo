package com.swayam.demo.springboot.googleauth.service;

import static org.bytedeco.leptonica.global.lept.L_CLONE;
import static org.bytedeco.leptonica.global.lept.boxaGetBox;
import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

import java.awt.Rectangle;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.leptonica.BOX;
import org.bytedeco.leptonica.BOXA;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.PIXA;
import org.bytedeco.tesseract.ETEXT_DESC;
import org.bytedeco.tesseract.ResultIterator;
import org.bytedeco.tesseract.TessBaseAPI;
import org.bytedeco.tesseract.global.tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.springboot.googleauth.model.old.Language;
import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordEntityTemplate;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;

import lombok.Value;

public class TesseractOcrWordAnalyser {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractOcrWordAnalyser.class);

    private final Path imagePath;
    private final Language language;
    private final String tessDataDirectory;

    public TesseractOcrWordAnalyser(Path imagePath, Language language, final String tessDataDirectory) {
	this.imagePath = imagePath;
	this.language = language;
	this.tessDataDirectory = tessDataDirectory;
    }

    public List<OcrWord> extractWordsFromImage(Function<Integer, OcrWordId> ocrWordIdGenerator) {
	LOGGER.info("Image file to analyse with Tesseract OCR: {}", imagePath);

	LOGGER.info("Analyzing image file for words with language {} and TESSDATA {}", language.name(), tessDataDirectory);

	List<OcrWord> ocrWords = new ArrayList<>();

	try (TessBaseAPI api = new TessBaseAPI();) {
	    int returnCode = api.Init(tessDataDirectory, language.name());
	    if (returnCode != 0) {
		throw new RuntimeException("could not initialize tesseract, error code: " + returnCode);
	    }

	    PIX image = pixRead(imagePath.toFile().getAbsolutePath());

	    api.SetImage(image);
	    int code = api.Recognize(new ETEXT_DESC());

	    if (code != 0) {
		throw new IllegalArgumentException("could not recognize text");
	    }

	    try (ResultIterator ri = api.GetIterator();) {
		int level = tesseract.RIL_WORD;
		int wordNumber = 1;
		Supplier<IntPointer> intPointerSupplier = () -> new IntPointer(new int[1]);
		do {
		    BytePointer ocrResult = ri.GetUTF8Text(level);
		    String ocrText = ocrResult.getString(Charset.forName("utf-8")).trim();

		    float confidence = ri.Confidence(level);
		    IntPointer x1 = intPointerSupplier.get();
		    IntPointer y1 = intPointerSupplier.get();
		    IntPointer x2 = intPointerSupplier.get();
		    IntPointer y2 = intPointerSupplier.get();
		    boolean foundRectangle = ri.BoundingBox(level, x1, y1, x2, y2);

		    if (!foundRectangle) {
			throw new IllegalArgumentException("Could not find any rectangle here");
		    }

		    OcrWordEntityTemplate ocrWord = new OcrWordEntityTemplate();
		    ocrWord.setX1(x1.get());
		    ocrWord.setY1(y1.get());
		    ocrWord.setX2(x2.get());
		    ocrWord.setY2(y2.get());
		    ocrWord.setConfidence(confidence);
		    ocrWord.setRawText(ocrText);
		    ocrWord.setOcrWordId(ocrWordIdGenerator.apply(wordNumber++));

		    LOGGER.info("ocrWord: {}", ocrWord);

		    ocrWords.add(ocrWord);

		    x1.deallocate();
		    y1.deallocate();
		    x2.deallocate();
		    y2.deallocate();
		    ocrResult.deallocate();

		} while (ri.Next(level));

		ri.deallocate();
	    }
	    api.End();
	    api.deallocate();
	    pixDestroy(image);
	}

	return ocrWords;
    }

    public List<String> getBoxStrings(Map<Integer, String> correctTextLookupBySequenceNumber, Collection<OcrWord> rawOcrWords) {
	LOGGER.trace("words: {}", rawOcrWords);
	ExtractedLineDetails extractedLineDetails = extractLinesFromImage();
	List<RawOcrLine> lines = extractedLineDetails.lines;

	LOGGER.trace("lines: {}", lines);

	Map<Integer, RawOcrLine> linesAsMap = lines.parallelStream().collect(Collectors.toMap(RawOcrLine::getLineNumber, Function.identity()));

	Map<Integer, List<OcrWord>> wordsGroupedByLineNumber = lines.parallelStream().collect(Collectors.toMap(RawOcrLine::getLineNumber, line -> {
	    return rawOcrWords.parallelStream().filter(rawOcrText -> {
		Rectangle originalLineArea = getWordArea(line.getX1(), line.getY1(), line.getX2(), line.getY2());
		int expandBy = 5;
		Rectangle expandedLineArea = new Rectangle(originalLineArea.x - expandBy, originalLineArea.y - expandBy, originalLineArea.width + expandBy, originalLineArea.height + expandBy);
		return expandedLineArea.contains(getWordArea(rawOcrText));
	    }).collect(Collectors.toList());
	}));

	LOGGER.trace("wordsGroupedByLineNumber: {}", wordsGroupedByLineNumber);

	return linesAsMap.keySet().stream().sorted().filter(lineNumber -> !wordsGroupedByLineNumber.get(lineNumber).isEmpty()).flatMap(lineNumber -> {

	    Supplier<Stream<OcrWord>> ocrWords = () -> wordsGroupedByLineNumber.get(lineNumber).parallelStream();
	    Supplier<IntStream> wordSequenceNumbers = () -> ocrWords.get().mapToInt(ocrWord -> ocrWord.getOcrWordId().getWordSequenceId());

	    int firstWordSequence = wordSequenceNumbers.get().min().getAsInt();
	    int lastWordSequence = wordSequenceNumbers.get().max().getAsInt();

	    Function<Integer, OcrWord> findWordFromSequenceNumber = sequenceNumber -> ocrWords.get().filter(ocrWord -> ocrWord.getOcrWordId().getWordSequenceId() == sequenceNumber).findAny().get();

	    OcrWord firstWord = findWordFromSequenceNumber.apply(firstWordSequence);
	    OcrWord lastWord = findWordFromSequenceNumber.apply(lastWordSequence);

	    int leftTopX = firstWord.getX1();
	    int leftTopY = linesAsMap.get(lineNumber).getY1();
	    int rightBottomX = lastWord.getX2();
	    int rightBottomY = linesAsMap.get(lineNumber).getY2();

	    int leftBottomX = leftTopX;
	    int leftBottomY = extractedLineDetails.imageHeight - rightBottomY;
	    // +5 pixels to be on the safer side
	    int rightTopX = rightBottomX + 5;
	    int rightTopY = extractedLineDetails.imageHeight - leftTopY;

	    String positionData = String.format(" %d %d %d %d 0", leftBottomX, leftBottomY, rightTopX, rightTopY);

	    List<String> boxes = ocrWords.get().sorted((OcrWord o1, OcrWord o2) -> o1.getOcrWordId().getWordSequenceId() - o2.getOcrWordId().getWordSequenceId()).map(rawOcrText -> {
		String ocrText;
		// lookup for corrected text
		if (correctTextLookupBySequenceNumber.containsKey(rawOcrText.getOcrWordId().getWordSequenceId())) {
		    ocrText = correctTextLookupBySequenceNumber.get(rawOcrText.getOcrWordId().getWordSequenceId());
		} else {
		    ocrText = rawOcrText.getRawText();
		}
		if (lastWordSequence == rawOcrText.getOcrWordId().getWordSequenceId()) {
		    return ocrText;
		}
		return ocrText + " ";
	    }).flatMap(text -> text.chars().mapToObj(c -> Character.toString((char) c))).map(text -> text + positionData).collect(Collectors.toList());
	    boxes.add("\t" + positionData);
	    return boxes.stream();
	}).collect(Collectors.toList());
    }

    private ExtractedLineDetails extractLinesFromImage() {

	LOGGER.info("Analyzing image file for lines...");
	List<RawOcrLine> lines;
	int imageHeight;

	try (TessBaseAPI api = new TessBaseAPI();) {
	    int returnCode = api.Init(tessDataDirectory, language.name());
	    if (returnCode != 0) {
		throw new RuntimeException("could not initialize tesseract, error code: " + returnCode);
	    }

	    PIX image = pixRead(imagePath.toFile().getAbsolutePath());

	    imageHeight = image.h();

	    api.SetImage(image);

	    BOXA boxes = api.GetComponentImages(tesseract.RIL_TEXTLINE, true, (PIXA) null, (IntBuffer) null);

	    LOGGER.info("Found {} textline image components.", boxes.n());

	    lines = IntStream.range(0, boxes.n()).mapToObj((int lineNumber) -> {
		BOX box = boxaGetBox(boxes, lineNumber, L_CLONE);
		api.SetRectangle(box.x(), box.y(), box.w(), box.h());
		BytePointer ocrResult = api.GetUTF8Text();
		String ocrLineText = ocrResult.getString().trim();
		ocrResult.deallocate();
		int confidence = api.MeanTextConf();
		int x1 = box.x();
		int y1 = box.y();
		int width = box.w();
		int height = box.h();

		// line number starts from 1
		RawOcrLine lineTextBox = new RawOcrLine(lineNumber + 1, x1, y1, x1 + width, y1 + height, confidence, ocrLineText);
		LOGGER.debug("lineTextBox: {}", lineTextBox);
		return lineTextBox;
	    }).collect(Collectors.toList());

	    api.End();
	    api.close();
	    pixDestroy(image);
	}

	return new ExtractedLineDetails(imageHeight, Collections.unmodifiableList(lines));

    }

    public static Rectangle getWordArea(OcrWord ocrWord) {
	return getWordArea(ocrWord.getX1(), ocrWord.getY1(), ocrWord.getX2(), ocrWord.getY2());
    }

    public static Rectangle getWordArea(int x1, int y1, int x2, int y2) {
	return new Rectangle(x1, y1, x2 - x1, y2 - y1);
    }

    private static class ExtractedLineDetails {
	private final int imageHeight;
	private final List<RawOcrLine> lines;

	ExtractedLineDetails(int imageHeight, List<RawOcrLine> lines) {
	    this.imageHeight = imageHeight;
	    this.lines = lines;
	}
    }

    @Value
    private static class RawOcrLine {

	// line number starts from 1
	private final int lineNumber;
	private final int x1;
	private final int y1;
	private final int x2;
	private final int y2;
	private final float meanConfidence;
	private final String lineText;

    }

}
