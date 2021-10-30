package com.swayam.demo.springboot.googleauth.rest;

import java.util.Collections;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.Author;
import com.swayam.demo.springboot.googleauth.repo.AuthorRepository;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
	this.authorRepository = authorRepository;
    }

    @GetMapping
    public Iterable<Author> getAuthors() {
	return authorRepository.findAll();
    }

    @GetMapping(value = "/{authorId}")
    public Author getAuthor(@PathVariable("authorId") final long authorId) {
	Optional<Author> author = authorRepository.findById(authorId);
	if (author.isEmpty()) {
	    return null;
	}
	return author.get();
    }

    @GetMapping(value = "/search")
    public Iterable<Author> searchAuthors(@RequestParam("country") final String country) {
	return Collections.emptyList();
    }

    @PostMapping
    public Author addAuthor(@RequestBody final Author author) {
	return authorRepository.save(author);
    }

}
