package com.swayam.demo.springboot.googleauth.rest;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.Book;
import com.swayam.demo.springboot.googleauth.repo.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
	this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Book> getBooks() {
	return bookRepository.findAll();
    }

    @GetMapping(value = "/{bookId}")
    public Book getBook(@PathVariable("bookId") final long bookId) {
	Optional<Book> book = bookRepository.findById(bookId);
	if (book.isEmpty()) {
	    return null;
	}
	return book.get();
    }

    @PostMapping
    public Book addBook(@RequestBody final Book book) {
	return bookRepository.save(book);
    }

}
