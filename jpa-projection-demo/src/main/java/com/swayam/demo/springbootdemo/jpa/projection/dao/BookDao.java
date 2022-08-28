package com.swayam.demo.springbootdemo.jpa.projection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

}
