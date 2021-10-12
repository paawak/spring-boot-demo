package com.swayam.demo.springbootdemo.cucumber.cukes;

import java.util.List;

import org.springframework.stereotype.Component;

import com.swayam.demo.springbootdemo.cucumber.model.Author;
import com.swayam.demo.springbootdemo.cucumber.model.Book;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Component
@ScenarioScope
@Data
public class SharedDTO {

    private String baseUrl;
    private List<Author> authors;
    private List<Book> books;
    private int booksAffected;

}
