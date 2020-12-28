package com.swayam.demo.springboot.googleauth.service;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.googleauth.model.old.Language;

@Service
public class TesseractInvokerServiceImpl implements TesseractInvokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractInvokerServiceImpl.class);

    private static final String UTF_8 = "utf-8";

    private final String tessdataDirectory;

    public TesseractInvokerServiceImpl(@Value("${app.config.ocr.tesseract.tessdata-location}") String tessdataDirectory,
	    @Value("${app.config.server.image-write-directory}") String imageWriteDirectory) {
	this.tessdataDirectory = tessdataDirectory;
    }

    @Override
    public String submitToOCR(Language language, Path imagePath) throws IOException {

	LOGGER.info("saved image file at: {}", imagePath);

	try (TessBaseAPI api = new TessBaseAPI();) {
	    int returnCode = api.Init(tessdataDirectory, language.name());
	    if (returnCode != 0) {
		throw new RuntimeException("could not initialize tesseract, error code: " + returnCode);
	    }

	    PIX image = pixRead(imagePath.toFile().getAbsolutePath());

	    api.SetImage(image);

	    BytePointer outText = api.GetUTF8Text();
	    String ocrText = outText.getString(UTF_8).trim();

	    LOGGER.info("ocrText: {}", ocrText);

	    api.End();
	    outText.close();
	    pixDestroy(image);

	    return ocrText;

	} finally {
	    Files.delete(imagePath);
	}

    }

}
