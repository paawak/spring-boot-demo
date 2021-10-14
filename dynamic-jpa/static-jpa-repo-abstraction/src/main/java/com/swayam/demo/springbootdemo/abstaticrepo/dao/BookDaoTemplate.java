package com.swayam.demo.springbootdemo.abstaticrepo.dao;

import java.util.List;

import com.swayam.demo.springbootdemo.abstaticrepo.model.Book;

public interface BookDaoTemplate {

    List<Book> findByNameContainingIgnoreCase(String name);

    int updateAuthor(int bookId, int authorId);

    Iterable<Book> findAll();

}
