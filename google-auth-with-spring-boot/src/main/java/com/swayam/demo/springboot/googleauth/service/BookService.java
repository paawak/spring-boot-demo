package com.swayam.demo.springboot.googleauth.service;

import java.util.List;

import com.swayam.demo.springboot.googleauth.model.old.Book;

public interface BookService {

    Book addBook(Book book);

    Book getBook(long bookId);

    List<Book> getBooks();

}
