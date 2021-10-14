package com.swayam.demo.springbootdemo.dynamicrepo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.dynamicrepo.model.Author;

public interface AuthorDao extends CrudRepository<Author, Integer> {

    List<Author> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

}
