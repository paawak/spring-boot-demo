package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.dao;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Author;

public interface AuthorDao extends CrudRepository<Author, Integer> {

}