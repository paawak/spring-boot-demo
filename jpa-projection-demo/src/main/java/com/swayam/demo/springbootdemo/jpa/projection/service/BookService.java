package com.swayam.demo.springbootdemo.jpa.projection.service;

import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

public interface BookService {

	Book getBook(Long bookId);

	Book saveOrUpdate(Book book);

}
