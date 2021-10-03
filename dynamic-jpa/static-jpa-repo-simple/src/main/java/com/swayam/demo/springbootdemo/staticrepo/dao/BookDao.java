package com.swayam.demo.springbootdemo.staticrepo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.staticrepo.model.Book;

public interface BookDao extends CrudRepository<Book, Integer> {

    List<Book> findByNameContainingIgnoreCase(String name);

    @Modifying
    @Query("update Book set author.id = :authorId where id = :bookId")
    int updateAuthor(int bookId, int authorId);

}
