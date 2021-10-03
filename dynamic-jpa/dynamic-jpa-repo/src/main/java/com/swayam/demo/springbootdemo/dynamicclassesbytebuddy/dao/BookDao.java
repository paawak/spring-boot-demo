package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.dao;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Book;

public interface BookDao extends CrudRepository<Book, Integer> {

}