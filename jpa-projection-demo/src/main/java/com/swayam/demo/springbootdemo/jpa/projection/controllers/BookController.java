package com.swayam.demo.springbootdemo.jpa.projection.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.jpa.projection.dao.BookDao;
import com.swayam.demo.springbootdemo.jpa.projection.model.AuthorView;
import com.swayam.demo.springbootdemo.jpa.projection.model.Book;
import com.swayam.demo.springbootdemo.jpa.projection.model.BookView;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookDao bookDao;

	public BookController(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@GetMapping
	public List<Book> getBooks() {
		return bookDao.findAll();
	}

	@GetMapping("/view")
	public List<BookView> getBooksForView() {
		return bookDao.findAllByIdNotNull();
	}

	@GetMapping("/author")
	public List<AuthorView> getAuthorAndChapters() {
		return bookDao.findAllAuthorAndChapters();
	}

	@GetMapping(path = "/{bookId}")
	public Book getBook(@PathVariable("bookId") Long bookId) {
		return bookDao.getReferenceById(bookId);
	}

	@PostMapping
	public Book saveNew(@RequestBody Book newBook) {
		return bookDao.save(newBook);
	}

}
