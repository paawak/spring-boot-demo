package com.swayam.demo.springbootdemo.jpa.projection.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.jpa.projection.model.Book;
import com.swayam.demo.springbootdemo.jpa.projection.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
	this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
	return bookService.getBooks();
    }

    @GetMapping(path = "/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
	return bookService.getBook(bookId);
    }

    @PostMapping
    public Book saveNew(@RequestBody Book newBook) {
	return bookService.saveOrUpdate(newBook);
    }

}
