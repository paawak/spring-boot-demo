package com.swayam.demo.springboot.googleauth.repo;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springboot.googleauth.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
