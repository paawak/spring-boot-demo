package com.swayam.demo.springbootdemo.dynamicrepo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicrepo.dao.BookDaoTemplate;
import com.swayam.demo.springbootdemo.dynamicrepo.model.BookTemplate;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookDaoTemplate bookDao;

    public BookController(BookDaoTemplate bookDao) {
	this.bookDao = bookDao;
    }

    @GetMapping
    public Iterable<? extends BookTemplate> getBooks() {
	return bookDao.findAll();
    }

    @GetMapping("/{name}")
    public List<BookTemplate> getBooksByName(@PathVariable("name") String name) {
	return bookDao.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("/author")
    public int updateAuthor(@RequestParam int bookId, @RequestParam int authorId) {
	return bookDao.updateAuthor(bookId, authorId);
    }

}
