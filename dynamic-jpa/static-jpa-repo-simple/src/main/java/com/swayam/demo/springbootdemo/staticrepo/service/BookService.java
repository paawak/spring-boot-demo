package com.swayam.demo.springbootdemo.staticrepo.service;

import com.swayam.demo.springbootdemo.staticrepo.model.Book;

public interface BookService {

    Iterable<Book> getBooks();

}
