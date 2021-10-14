package com.swayam.demo.springbootdemo.abstaticrepo.cukes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.swayam.demo.springbootdemo.abstaticrepo.model.Author;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthorSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SharedDTO sharedDto;

    @Given("The application is available at {string}")
    public void applicationBaseUrl(String baseUrl) {
	sharedDto.setBaseUrl(baseUrl);
    }

    @Given("Health check is fine at {string}")
    public void checkApplicationHealth(String uri) {
	Map<String, String> response = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<Map<String, String>>() {
		}).getBody();
	assertEquals("UP", response.get("status"));
    }

    @When("I fetch authors at {string}")
    public void fetchAuthors(String uri) {
	List<Author> authors = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<Author>>() {
		}).getBody();
	sharedDto.setAuthors(authors);
    }

    @When("I search for author by name at {string}")
    public void searchByName(String uri) {
	List<Author> authors = restTemplate.exchange(sharedDto.getBaseUrl() + uri, HttpMethod.GET, null,
		new ParameterizedTypeReference<List<Author>>() {
		}).getBody();
	sharedDto.setAuthors(authors);
    }

    @Then("I should find {int} authors")
    public void fetchAuthors(int authorCount) {
	assertEquals(authorCount, sharedDto.getAuthors().size());
    }

}
