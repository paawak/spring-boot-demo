package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Book;

public interface BookService {

    Iterable<Book> getBooks();

}
