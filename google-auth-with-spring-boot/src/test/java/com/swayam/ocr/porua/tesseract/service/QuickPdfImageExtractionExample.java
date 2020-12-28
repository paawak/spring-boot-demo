package com.swayam.ocr.porua.tesseract.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Code taken from: <a href=
 * "https://stackoverflow.com/questions/8705163/extract-images-from-pdf-using-pdfbox">https://stackoverflow.com/questions/8705163/extract-images-from-pdf-using-pdfbox</a>
 * 
 */
public class QuickPdfImageExtractionExample {

    public static void main(String[] args) throws IOException {
	PDDocument document = PDDocument.load(new File("/home/paawak/Downloads/rajshekhar.mahabharat.bangla.pdf"));
	if (true) {
	    smarterFunctionalExtraction(document);
	} else {
	    simpleRunOfTheMillExtraction(document);
	}
    }

    private static void smarterFunctionalExtraction(PDDocument document) {

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
		}).forEach((BufferedImage image) -> {
		    String imageFileName = String.format("/kaaj/source/porua/tesseract-ocr-rest/target/img/%d.png", pageCount.getAndIncrement());
		    try {
			ImageIO.write(image, "png", new File(imageFileName));
		    } catch (IOException e) {
			throw new RuntimeException(e);
		    }
		});
    }

    private static void simpleRunOfTheMillExtraction(PDDocument document) throws IOException {
	int pageCount = 1;
	PDPageTree pdPageTree = document.getPages();
	for (PDPage page : pdPageTree) {
	    PDResources pdResources = page.getResources();
	    for (COSName c : pdResources.getXObjectNames()) {
		PDXObject o = pdResources.getXObject(c);
		if (o instanceof PDImageXObject) {
		    File file = new File("/kaaj/source/porua/tesseract-ocr-rest/target/img/" + pageCount++ + ".png");
		    ImageIO.write(((PDImageXObject) o).getImage(), "png", file);
		}
	    }
	}
    }

}
