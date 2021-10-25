package com.swayam.demo.springboot.googleauth.repo;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springboot.googleauth.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {

}
