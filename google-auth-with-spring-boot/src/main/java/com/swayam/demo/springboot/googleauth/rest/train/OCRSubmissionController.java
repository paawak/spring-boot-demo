package com.swayam.demo.springboot.googleauth.rest.train;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;
import com.swayam.demo.springboot.googleauth.service.FileSystemUtil;
import com.swayam.demo.springboot.googleauth.service.ImageProcessor;

@RestController
@RequestMapping("/ocr/train/submit")
@Secured("ADMIN_ROLE")
public class OCRSubmissionController {

    private static final Logger LOG = LoggerFactory.getLogger(OCRSubmissionController.class);

    private final ImageProcessor imageProcessor;
    private final FileSystemUtil fileSystemUtil;

    public OCRSubmissionController(ImageProcessor imageProcessor, FileSystemUtil fileSystemUtil) {
	this.imageProcessor = imageProcessor;
	this.fileSystemUtil = fileSystemUtil;
    }

    @PostMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OcrWordOutputDto> submitPageAndAnalyzeWords(@RequestParam("bookId") final Long bookId, @RequestParam("pageNumber") final Integer pageNumber,
	    @RequestParam("image") final MultipartFile image) throws IOException, URISyntaxException {

	String imageFileName = image.getOriginalFilename();
	LOG.info("BookId: {}, PageNumber: {}, Uploaded fileName: {}", bookId, pageNumber, imageFileName);

	Path savedImagePath = fileSystemUtil.saveMultipartFileAsImage(image);

	return imageProcessor.submitPageForAnalysis(bookId, pageNumber, imageFileName, savedImagePath);

    }

    @PostMapping(value = "/pdf", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadEBookInPdfFormat(@RequestParam("bookId") final String bookIdAsString, @RequestParam("image") MultipartFile eBookAsPdf) throws IOException {

	String eBookName = eBookAsPdf.getOriginalFilename();
	MediaType mediaType = MediaType.parseMediaType(eBookAsPdf.getContentType());

	LOG.info("bookId: {}", bookIdAsString);
	LOG.info("FileName: {}, ContentType: {}, Size: {}", eBookName, mediaType, eBookAsPdf.getSize());

	if (!MediaType.APPLICATION_PDF.equals(mediaType)) {
	    return ResponseEntity.badRequest().body("Only PDF docs supported. Unsupported content-type: " + mediaType);
	}

	Path savedEBookPath = fileSystemUtil.saveMultipartFileAsImage(eBookAsPdf);

	int extractedPageCount = imageProcessor.processEBookInPdf(Long.valueOf(bookIdAsString), eBookName, savedEBookPath);

	return ResponseEntity.ok(Integer.toString(extractedPageCount));
    }

}
