package com.swayam.demo.springbootdemo.staticrepo.service;

import com.swayam.demo.springbootdemo.staticrepo.model.Author;

public interface AuthorService {

    Iterable<Author> getAuthors();

}
