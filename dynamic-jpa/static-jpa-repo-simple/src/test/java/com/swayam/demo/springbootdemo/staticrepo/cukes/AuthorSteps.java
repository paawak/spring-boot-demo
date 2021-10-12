package com.swayam.demo.springbootdemo.staticrepo.cukes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.swayam.demo.springbootdemo.staticrepo.model.Author;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthorSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthorResponse authorResponse;

    @Given("The application health is fine at {string}")
    public void checkApplicationHealth(String uri) {
	Map<String, String> response =
		restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, String>>() {
		}).getBody();
	assertEquals("UP", response.get("status"));
    }

    @When("I fetch authors at {string}")
    public void fetchAuthors(String uri) {
	List<Author> authors =
		restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Author>>() {
		}).getBody();
	authorResponse.setAuthors(authors);
    }

    @Then("I should find {int} authors")
    public void fetchAuthors(int authorCount) {
	assertEquals(2, authorResponse.getAuthors().size());
    }

}
