package com.swayam.demo.springbootdemo.jpa.projection.service;

import java.util.List;

import com.swayam.demo.springbootdemo.jpa.projection.dao.BookDao.BookView;
import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

public interface BookService {

	List<Book> getBooks();

	Book getBook(Long bookId);

	Book saveOrUpdate(Book book);

	List<BookView> getBooksForView();

}
