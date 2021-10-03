package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.Author;
import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service.AuthorService;

@RestController
@RequestMapping("/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
	this.authorService = authorService;
    }

    @GetMapping
    public Iterable<Author> getAuthors() {
	return authorService.getAuthors();
    }

}
