package com.swayam.demo.springbootdemo.abstaticrepo.cukes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.swayam.demo.springbootdemo.abstaticrepo.model.BookTemplate;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SharedDTO sharedDto;

    @When("I fetch books at {string}")
    public void fetchBooks(String uri) {
	List<BookTemplate> books = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<BookTemplate>>() {
		}).getBody();
	sharedDto.setBooks(books);
    }

    @When("I search for books by name at {string}")
    public void searchByName(String uri) {
	List<BookTemplate> books = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<BookTemplate>>() {
		}).getBody();
	sharedDto.setBooks(books);
    }

    @When("I update the author of a book at {string}")
    public void updateAuthorInBook(String uri) {
	int booksAffected =
		restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.PUT, null, Integer.class).getBody();
	sharedDto.setBooksAffected(booksAffected);
    }

    @Then("I should find {int} books")
    public void fetchAuthors(int bookCount) {
	assertEquals(bookCount, sharedDto.getBooks().size());
    }

    @Then("{int} book should be affected")
    public void countBooksAffected(int bookCount) {
	assertEquals(bookCount, sharedDto.getBooksAffected());
    }

    @Then("The author first name should be {string}")
    public void verifyAuthorFirstName(String authorFirstName) {
	assertEquals(1, sharedDto.getBooks().size());
	assertEquals(authorFirstName, sharedDto.getBooks().get(0).getAuthor().getFirstName());
    }

}
