package com.swayam.demo.springbootdemo.staticrepo.dao;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.staticrepo.model.Author;

public interface AuthorDao extends CrudRepository<Author, Integer> {

}