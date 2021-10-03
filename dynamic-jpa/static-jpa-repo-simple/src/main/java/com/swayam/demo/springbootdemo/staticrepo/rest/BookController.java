package com.swayam.demo.springbootdemo.staticrepo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.staticrepo.dao.BookDao;
import com.swayam.demo.springbootdemo.staticrepo.model.Book;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookDao bookDao;

    public BookController(BookDao bookDao) {
	this.bookDao = bookDao;
    }

    @GetMapping
    public Iterable<Book> getBooks() {
	return bookDao.findAll();
    }

    @GetMapping("/{name}")
    public List<Book> getBooksByName(@PathVariable("name") String name) {
	return bookDao.findByNameContainingIgnoreCase(name);
    }

}
