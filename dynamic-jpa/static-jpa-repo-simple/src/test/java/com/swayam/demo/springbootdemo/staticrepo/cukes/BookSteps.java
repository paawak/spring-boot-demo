package com.swayam.demo.springbootdemo.staticrepo.cukes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.swayam.demo.springbootdemo.staticrepo.model.Book;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SharedDTO sharedDto;

    @When("I fetch books at {string}")
    public void fetchBooks(String uri) {
	List<Book> books = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<Book>>() {
		}).getBody();
	sharedDto.setBooks(books);
    }

    @When("I search for books by name at {string}")
    public void searchByName(String uri) {
	List<Book> books = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<Book>>() {
		}).getBody();
	sharedDto.setBooks(books);
    }

    @Then("I should find {int} books")
    public void fetchAuthors(int bookCount) {
	assertEquals(bookCount, sharedDto.getBooks().size());
    }

}
