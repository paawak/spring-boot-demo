package com.swayam.demo.springbootdemo.staticrepo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.staticrepo.dao.AuthorDao;
import com.swayam.demo.springbootdemo.staticrepo.model.Author;

@RestController
public class AuthorController {

    private final AuthorDao authorDao;

    public AuthorController(AuthorDao authorDao) {
	this.authorDao = authorDao;
    }

    @GetMapping(path = "/v1/author")
    public Iterable<Author> getAuthors() {
	return authorDao.findAll();
    }

    @GetMapping(path = "/v1/author/{name}")
    public List<Author> getAuthorsByName(@PathVariable("name") String name) {
	return authorDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

}
