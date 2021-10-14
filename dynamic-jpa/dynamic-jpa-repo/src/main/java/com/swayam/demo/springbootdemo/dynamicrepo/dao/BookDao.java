package com.swayam.demo.springbootdemo.dynamicrepo.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.springbootdemo.dynamicrepo.model.Book;

public interface BookDao extends BookDaoTemplate, CrudRepository<Book, Integer> {

    @Override
    @Transactional
    @Modifying
    @Query("update Book set author.id = :authorId where id = :bookId")
    int updateAuthor(int bookId, int authorId);

}
