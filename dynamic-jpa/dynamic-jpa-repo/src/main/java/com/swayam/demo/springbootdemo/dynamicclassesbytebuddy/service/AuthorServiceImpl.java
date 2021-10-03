package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.dao.AuthorDao;
import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Author;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
	this.authorDao = authorDao;
    }

    @Override
    public Iterable<Author> getAuthors() {
	return authorDao.findAll();
    }

}
