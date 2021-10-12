package com.swayam.demo.springbootdemo.staticrepo.cukes;

import java.util.List;

import org.springframework.stereotype.Component;

import com.swayam.demo.springbootdemo.staticrepo.model.Author;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Component
@ScenarioScope
@Data
public class AuthorResponse {

    private List<Author> authors;

}
