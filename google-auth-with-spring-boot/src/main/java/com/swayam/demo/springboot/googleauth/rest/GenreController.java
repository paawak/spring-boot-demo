package com.swayam.demo.springboot.googleauth.rest;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.Genre;
import com.swayam.demo.springboot.googleauth.repo.GenreRepository;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
	this.genreRepository = genreRepository;
    }

    @GetMapping
    public Iterable<Genre> getGenres() {
	return genreRepository.findAll();
    }

    @GetMapping(value = "/{genreId}")
    public Genre getGenre(@PathVariable("genreId") final long genreId) {
	Optional<Genre> genre = genreRepository.findById(genreId);
	if (genre.isEmpty()) {
	    return null;
	}
	return genre.get();
    }

    @PostMapping
    public Genre addGenre(@RequestBody final Genre genre) {
	return genreRepository.save(genre);
    }

}
