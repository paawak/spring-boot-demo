package com.swayam.demo.springboot.googleauth.repo;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springboot.googleauth.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
