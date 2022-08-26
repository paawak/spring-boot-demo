package com.swayam.demo.springbootdemo.jpa.projection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.jpa.projection.dao.BookDao;
import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookRepo;

    @Autowired
    public BookServiceImpl(BookDao bookRepo) {
	this.bookRepo = bookRepo;
    }

    @Override
    public Book getBook(Long bookId) {
	return bookRepo.getReferenceById(bookId);
    }

    @Override
    public Book saveOrUpdate(Book book) {
	return bookRepo.save(book);
    }

}
