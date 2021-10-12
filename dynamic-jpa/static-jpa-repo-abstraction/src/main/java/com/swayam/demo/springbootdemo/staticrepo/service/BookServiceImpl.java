package com.swayam.demo.springbootdemo.staticrepo.service;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.staticrepo.dao.BookDao;
import com.swayam.demo.springbootdemo.staticrepo.model.Book;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
	this.bookDao = bookDao;
    }

    @Override
    public Iterable<Book> getBooks() {
	return bookDao.findAll();
    }

}
