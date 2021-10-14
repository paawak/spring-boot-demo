package com.swayam.demo.springbootdemo.dynamicrepo.cukes;

import java.util.List;

import org.springframework.stereotype.Component;

import com.swayam.demo.springbootdemo.dynamicrepo.model.Author;
import com.swayam.demo.springbootdemo.dynamicrepo.model.BookTemplate;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Component
@ScenarioScope
@Data
public class SharedDTO {

    private String baseUrl;
    private List<Author> authors;
    private List<BookTemplateImpl> books;
    private int booksAffected;

    @Data
    static class BookTemplateImpl implements BookTemplate {

	private int id;

	private String name;

	private Author author;

    }

}