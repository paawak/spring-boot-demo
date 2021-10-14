package com.swayam.demo.springbootdemo.abstaticrepo.dao;

import java.util.List;

import com.swayam.demo.springbootdemo.abstaticrepo.model.BookTemplate;

public interface BookDaoTemplate {

    List<BookTemplate> findByNameContainingIgnoreCase(String name);

    int updateAuthor(int bookId, int authorId);

    Iterable<? extends BookTemplate> findAll();

}
