package com.swayam.demo.springbootdemo.staticrepo.dao;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.staticrepo.model.Book;

public interface BookDao extends CrudRepository<Book, Integer> {

}