package com.swayam.ocr.porua.tesseract.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.swayam.demo.springboot.googleauth.service.BookServiceImpl;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class BookServiceImplIntegrationTest {

    @Autowired
    private BookServiceImpl testClass;

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
    void testAddBook() {
	// given
	Book book = new Book();
	book.setName("TEST BOOK 44");
	book.setLanguage(Language.ben);

	// when
	Book result = testClass.addBook(book);

	// then
	assertEquals(3, result.getId());
	assertEquals("TEST BOOK 44", result.getName());
	assertEquals(Language.ben, result.getLanguage());
    }

    @Test
    void testGetBooks() {
	// given
	Book book1 = new Book();
	book1.setId(1);
	book1.setName("TEST BOOK 1");
	book1.setLanguage(Language.ben);

	Book book2 = new Book();
	book2.setId(2);
	book2.setName("TEST BOOK 2");
	book2.setLanguage(Language.eng);

	List<Book> expected = Arrays.asList(book1, book2);

	// when
	Iterable<Book> results = testClass.getBooks();

	// then
	assertEquals(expected, results);
    }

}
