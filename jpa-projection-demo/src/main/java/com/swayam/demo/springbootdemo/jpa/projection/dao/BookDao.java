package com.swayam.demo.springbootdemo.jpa.projection.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<BookView> findAllByIdNotNull();

	record BookView(Long id, String title, Long authorId, String authorFirstName) {

	}

}
