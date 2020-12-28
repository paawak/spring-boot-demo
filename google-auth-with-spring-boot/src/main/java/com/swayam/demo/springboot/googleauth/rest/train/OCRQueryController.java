package com.swayam.demo.springboot.googleauth.rest.train;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.old.Book;
import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.PageImage;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;
import com.swayam.demo.springboot.googleauth.service.BookService;
import com.swayam.demo.springboot.googleauth.service.FileSystemUtil;
import com.swayam.demo.springboot.googleauth.service.OcrWordService;
import com.swayam.demo.springboot.googleauth.service.PageService;
import com.swayam.demo.springboot.googleauth.service.TesseractOcrWordAnalyser;

@RestController
@RequestMapping("/ocr/train/query")
public class OCRQueryController {

    private static final Logger LOG = LoggerFactory.getLogger(OCRQueryController.class);

    private final BookService bookService;
    private final PageService pageService;
    private final OcrWordService ocrDataStoreService;
    private final FileSystemUtil fileSystemUtil;

    public OCRQueryController(BookService bookService, PageService pageService, OcrWordService ocrDataStoreService, FileSystemUtil fileSystemUtil) {
	this.bookService = bookService;
	this.pageService = pageService;
	this.ocrDataStoreService = ocrDataStoreService;
	this.fileSystemUtil = fileSystemUtil;
    }

    @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
	return bookService.getBooks();
    }

    @GetMapping(value = "/book/{bookId}/page-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getPagesInBook(@PathVariable("bookId") final long bookId) {
	return pageService.getPageCount(bookId);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PageImage> getPages(@RequestParam("bookId") final long bookId) {
	return pageService.getPages(bookId);
    }

    @GetMapping(value = "/word", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OcrWordOutputDto> getOcrWords(@AuthenticationPrincipal Authentication authentication, @RequestParam("bookId") final long bookId,
	    @RequestParam("pageImageId") final long pageImageId) {
	UserDetails userDetails = (UserDetails) authentication.getDetails();
	LOG.info("Retrieving OCR Words for Book Id {} and PageId {}", bookId, pageImageId);
	return ocrDataStoreService.getWords(bookId, pageImageId, userDetails);
    }

    @GetMapping(value = "/word/image")
    public ResponseEntity<byte[]> getOcrWordImage(@RequestParam("bookId") final long bookId, @RequestParam("pageImageId") final long pageImageId, @RequestParam("wordSequenceId") int wordSequenceId)
	    throws IOException {
	String pageImageName = pageService.getPageImage(pageImageId).getName();
	Path imagePath = fileSystemUtil.getImageSaveLocation(pageImageName);

	OcrWord ocrText = ocrDataStoreService.getWord(new OcrWordId(bookId, pageImageId, wordSequenceId));
	BufferedImage fullImage = ImageIO.read(Files.newInputStream(imagePath));
	Rectangle wordArea = TesseractOcrWordAnalyser.getWordArea(ocrText);
	BufferedImage wordImage = fullImage.getSubimage(wordArea.x, wordArea.y, wordArea.width, wordArea.height);
	String[] tokens = imagePath.toFile().getName().split("\\.");
	String extension = tokens[tokens.length - 1];

	LOG.trace("serving ocr word image for id: {} and page image name: {}, location: {}, extension: {}", wordSequenceId, pageImageName, imagePath, extension);

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ImageIO.write(wordImage, extension, bos);
	bos.flush();
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.set("content-type", "image/" + extension);
	return new ResponseEntity<byte[]>(bos.toByteArray(), responseHeaders, HttpStatus.OK);
    }

}
