package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Author;

public interface AuthorService {

    Iterable<Author> getAuthors();

}
