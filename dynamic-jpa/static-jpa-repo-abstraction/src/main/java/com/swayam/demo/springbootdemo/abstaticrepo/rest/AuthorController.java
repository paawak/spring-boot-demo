package com.swayam.demo.springbootdemo.abstaticrepo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.abstaticrepo.dao.AuthorDao;
import com.swayam.demo.springbootdemo.abstaticrepo.model.Author;

@RestController
@RequestMapping("/v1/author")
public class AuthorController {

    private final AuthorDao authorDao;

    public AuthorController(AuthorDao authorDao) {
	this.authorDao = authorDao;
    }

    @GetMapping
    public Iterable<Author> getAuthors() {
	return authorDao.findAll();
    }

    @GetMapping("/{name}")
    public List<Author> getAuthorsByName(@PathVariable String name) {
	return authorDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

}
