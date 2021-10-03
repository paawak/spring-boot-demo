package com.swayam.demo.springbootdemo.staticrepo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.staticrepo.model.Book;
import com.swayam.demo.springbootdemo.staticrepo.service.BookService;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
	this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> getBooks() {
	return bookService.getBooks();
    }

}
