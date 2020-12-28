package com.swayam.demo.springboot.googleauth.rest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swayam.demo.springboot.googleauth.model.old.Language;
import com.swayam.demo.springboot.googleauth.service.FileSystemUtil;
import com.swayam.demo.springboot.googleauth.service.TesseractInvokerService;

@RestController
@RequestMapping("/ocr/simple")
public class SimpleOCRController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleOCRController.class);

    private static final List<MediaType> SUPPORTED_CONTENT_TYPES = Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG, MediaType.APPLICATION_OCTET_STREAM, new MediaType("image", "tiff"));

    private final TesseractInvokerService tesseractInvokerService;
    private final FileSystemUtil fileSystemUtil;

    public SimpleOCRController(TesseractInvokerService tesseractInvokerService, FileSystemUtil fileSystemUtil) {
	this.tesseractInvokerService = tesseractInvokerService;
	this.fileSystemUtil = fileSystemUtil;

    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadImageFile(@RequestParam("language") Language language, @RequestParam("image") MultipartFile image) throws IOException {
	LOGGER.info("language: {}", language);

	LOGGER.info("FileName: {}, ContentType: {}, Size: {}", image.getOriginalFilename(), image.getContentType(), image.getSize());

	MediaType contentType = MediaType.parseMediaType(image.getContentType());

	if (!SUPPORTED_CONTENT_TYPES.contains(contentType)) {
	    LOGGER.error("Un-supported ContentType: {}", contentType);
	    return ResponseEntity.badRequest().body("unsupported content-type: " + contentType);
	}

	Path imageSavedPath = fileSystemUtil.saveMultipartFileAsImage(image);

	return ResponseEntity.ok(tesseractInvokerService.submitToOCR(language, imageSavedPath));
    }

}
