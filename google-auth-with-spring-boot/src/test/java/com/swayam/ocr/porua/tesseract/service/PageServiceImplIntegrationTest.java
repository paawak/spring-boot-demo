package com.swayam.ocr.porua.tesseract.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.swayam.demo.springboot.googleauth.model.old.Book;
import com.swayam.demo.springboot.googleauth.model.old.Language;
import com.swayam.demo.springboot.googleauth.model.old.PageImage;
import com.swayam.demo.springboot.googleauth.service.PageServiceImpl;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class PageServiceImplIntegrationTest {

    @Autowired
    private PageServiceImpl testClass;

    @Autowired
    private JdbcOperations jdbcTemplate;

    @BeforeEach
    void setupBookAndRawImage() {
	jdbcTemplate.execute("TRUNCATE TABLE book");
	jdbcTemplate.update("INSERT INTO book (id, name, language) VALUES (1, 'TEST BOOK 1', 'ben')");
	jdbcTemplate.update("INSERT INTO book (id, name, language) VALUES (2, 'TEST BOOK 2', 'eng')");
	jdbcTemplate.update("INSERT INTO page_image (id, book_id, name, page_number) VALUES (1, 1, 'TEST IMAGE 1.jpg', 1)");
	jdbcTemplate.update("INSERT INTO page_image (id, book_id, name, page_number) VALUES (2, 1, 'TEST IMAGE 2.jpg', 2)");
    }

    @Test
    void testGetPages() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage1 = new PageImage();
	pageImage1.setId(1);
	pageImage1.setBook(book);
	pageImage1.setName("TEST IMAGE 1.jpg");
	pageImage1.setPageNumber(1);

	PageImage pageImage2 = new PageImage();
	pageImage2.setId(2);
	pageImage2.setBook(book);
	pageImage2.setName("TEST IMAGE 2.jpg");
	pageImage2.setPageNumber(2);

	// when
	List<PageImage> results = testClass.getPages(1);

	// then
	assertEquals(Arrays.asList(pageImage1, pageImage2), results);
    }

    @Test
    void testGetPages_ignored_completed() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage1 = new PageImage();
	pageImage1.setId(1);
	pageImage1.setBook(book);
	pageImage1.setName("TEST IMAGE 1.jpg");
	pageImage1.setPageNumber(1);

	PageImage pageImage2 = new PageImage();
	pageImage2.setId(2);
	pageImage2.setBook(book);
	pageImage2.setName("TEST IMAGE 2.jpg");
	pageImage2.setPageNumber(2);

	PageImage pageImage3 = new PageImage();
	pageImage3.setId(3);
	pageImage3.setBook(book);
	pageImage3.setName("TEST IMAGE 3.jpg");
	pageImage3.setPageNumber(3);
	pageImage3.setCorrectionCompleted(true);
	testClass.addPageImage(pageImage3);

	PageImage pageImage4 = new PageImage();
	pageImage4.setId(4);
	pageImage4.setBook(book);
	pageImage4.setName("TEST IMAGE 4.jpg");
	pageImage4.setPageNumber(4);
	testClass.addPageImage(pageImage4);

	PageImage pageImage5 = new PageImage();
	pageImage5.setId(5);
	pageImage5.setBook(book);
	pageImage5.setName("TEST IMAGE 5.jpg");
	pageImage5.setPageNumber(5);
	pageImage5.setIgnored(true);
	testClass.addPageImage(pageImage5);

	// when
	List<PageImage> results = testClass.getPages(1);

	// then
	assertEquals(Arrays.asList(pageImage1, pageImage2, pageImage4), results);
    }

    @Test
    void testGetPageCount() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage1 = new PageImage();
	pageImage1.setBook(book);
	pageImage1.setName("TEST IMAGE 3.jpg");
	pageImage1.setPageNumber(10);
	testClass.addPageImage(pageImage1);

	PageImage pageImage2 = new PageImage();
	pageImage2.setBook(book);
	pageImage2.setName("TEST IMAGE 4.jpg");
	pageImage2.setPageNumber(20);
	testClass.addPageImage(pageImage2);

	// when
	int result = testClass.getPageCount(1);

	// then
	assertEquals(4, result);
    }

    @Test
    void testAddPageImage() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage = new PageImage();
	pageImage.setBook(book);
	pageImage.setName("TEST IMAGE TIFF 1");
	pageImage.setPageNumber(10);

	// when
	PageImage result = testClass.addPageImage(pageImage);

	// then
	assertEquals(3, result.getId());
	assertEquals(book, result.getBook());
	assertEquals("TEST IMAGE TIFF 1", result.getName());
	assertEquals(10, result.getPageNumber());
    }

    @Test
    void testMarkPageAsIgnored() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage1 = new PageImage();
	pageImage1.setId(1);
	pageImage1.setBook(book);
	pageImage1.setName("TEST IMAGE 1.jpg");
	pageImage1.setPageNumber(1);

	PageImage pageImage2 = new PageImage();
	pageImage2.setId(2);
	pageImage2.setBook(book);
	pageImage2.setName("TEST IMAGE 2.jpg");
	pageImage2.setPageNumber(2);

	// when
	int result = testClass.markPageAsIgnored(1);

	// then
	assertEquals(1, result);
	Boolean ignored = jdbcTemplate.queryForObject("select ignored from page_image where id = ?", Boolean.class, 1);
	assertTrue(ignored);
	Boolean completed = jdbcTemplate.queryForObject("select correction_completed from page_image where id = ?", Boolean.class, 1);
	assertFalse(completed);
	List<PageImage> pages = testClass.getPages(1);
	assertEquals(Arrays.asList(pageImage2), pages);
    }

    @Test
    void testMarkPageAsCorrectionCompleted() {
	// given
	Book book = new Book();
	book.setId(1);
	book.setName("TEST BOOK 1");
	book.setLanguage(Language.ben);

	PageImage pageImage1 = new PageImage();
	pageImage1.setId(1);
	pageImage1.setBook(book);
	pageImage1.setName("TEST IMAGE 1.jpg");
	pageImage1.setPageNumber(1);

	PageImage pageImage2 = new PageImage();
	pageImage2.setId(2);
	pageImage2.setBook(book);
	pageImage2.setName("TEST IMAGE 2.jpg");
	pageImage2.setPageNumber(2);

	// when
	int result = testClass.markPageAsCorrectionCompleted(2);

	// then
	assertEquals(1, result);
	Boolean ignored = jdbcTemplate.queryForObject("select ignored from page_image where id = ?", Boolean.class, 2);
	assertFalse(ignored);
	Boolean completed = jdbcTemplate.queryForObject("select correction_completed from page_image where id = ?", Boolean.class, 2);
	assertTrue(completed);
	List<PageImage> pages = testClass.getPages(1);
	assertEquals(Arrays.asList(pageImage1), pages);
    }

}
