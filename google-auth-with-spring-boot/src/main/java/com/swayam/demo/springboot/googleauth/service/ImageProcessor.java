package com.swayam.demo.springboot.googleauth.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.googleauth.model.old.Book;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.PageImage;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;

@Service
public class ImageProcessor {

    private static final String IMAGE_TYPE = "png";

    private final TaskExecutor taskExecutor;
    private final BookService bookService;
    private final PageService pageService;
    private final OcrWordService ocrDataStoreService;
    private final String tessDataDirectory;

    public ImageProcessor(TaskExecutor taskExecutor, BookService bookService, PageService pageService, OcrWordService ocrDataStoreService,
	    @Value("${app.config.ocr.tesseract.tessdata-location}") String tessDataDirectory) {
	this.taskExecutor = taskExecutor;
	this.bookService = bookService;
	this.pageService = pageService;
	this.ocrDataStoreService = ocrDataStoreService;
	this.tessDataDirectory = tessDataDirectory;
    }

    public int processEBookInPdf(long bookId, final String fileName, final Path eBookPdfDownloadPath) {

	PDDocument document;
	try {
	    document = PDDocument.load(eBookPdfDownloadPath.toFile());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	String pageNameTemplate = fileName.substring(0, fileName.length() - 3) + "_%d." + IMAGE_TYPE;
	Book book = bookService.getBook(bookId);

	AtomicInteger pageCount = new AtomicInteger(1);

	PDPageTree pdPageTree = document.getPages();
	StreamSupport.stream(pdPageTree.spliterator(), false).map(PDPage::getResources)
		.flatMap((PDResources pdResources) -> StreamSupport.stream(pdResources.getXObjectNames().spliterator(), false).map((COSName cosName) -> {
		    try {
			return pdResources.getXObject(cosName);
		    } catch (IOException e) {
			throw new RuntimeException(e);
		    }
		})).filter((PDXObject pdxObject) -> pdxObject instanceof PDImageXObject).map(pdxObject -> {
		    try {
			return ((PDImageXObject) pdxObject).getImage();
		    } catch (IOException e) {
			throw new RuntimeException(e);
		    }
		}).forEach((BufferedImage pageImage) -> {
		    int pageNumber = pageCount.getAndIncrement();
		    String pageImageName = String.format(pageNameTemplate, pageNumber);
		    Path imageLocation = eBookPdfDownloadPath.getParent().resolve(pageImageName);
		    taskExecutor.execute(() -> submitPdfPageForAnalysis(book, pageImage, pageNumber, pageImageName, imageLocation));
		});

	return pageCount.intValue();

    }

    public List<OcrWordOutputDto> submitPageForAnalysis(final long bookId, final int pageNumber, final String imageFileName, final Path savedImagePath) {
	Book book = bookService.getBook(bookId);
	return submitPageForAnalysis(book, pageNumber, imageFileName, savedImagePath);
    }

    private List<OcrWordOutputDto> submitPageForAnalysis(final Book book, final int pageNumber, final String imageFileName, final Path savedImagePath) {
	PageImage newPageImage = new PageImage();
	newPageImage.setName(imageFileName);
	newPageImage.setPageNumber(pageNumber);
	newPageImage.setBook(book);
	long imageFileId = pageService.addPageImage(newPageImage).getId();

	return new TesseractOcrWordAnalyser(savedImagePath, book.getLanguage(), tessDataDirectory).extractWordsFromImage((wordSequenceId) -> new OcrWordId(book.getId(), imageFileId, wordSequenceId))
		.stream().map(rawText -> ocrDataStoreService.addOcrWord(rawText)).map(ocrWord -> {
		    OcrWordOutputDto dto = new OcrWordOutputDto();
		    BeanUtils.copyProperties(ocrWord, dto);
		    return dto;
		}).collect(Collectors.toUnmodifiableList());

    }

    private void submitPdfPageForAnalysis(Book book, BufferedImage pageImage, int pageNumber, String imageFileName, Path imageLocation) {
	try {
	    ImageIO.write(pageImage, IMAGE_TYPE, imageLocation.toFile());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	submitPageForAnalysis(book, pageNumber, imageFileName, imageLocation);
    }

}
