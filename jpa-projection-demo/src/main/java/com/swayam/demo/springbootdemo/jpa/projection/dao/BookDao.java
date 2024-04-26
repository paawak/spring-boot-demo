package com.swayam.demo.springbootdemo.jpa.projection.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.jpa.projection.model.AuthorView;
import com.swayam.demo.springbootdemo.jpa.projection.model.Book;
import com.swayam.demo.springbootdemo.jpa.projection.model.BookView;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<BookView> findAllByIdNotNull();

	@Query("""
			SELECT
			new com.swayam.demo.springbootdemo.jpa.projection.model.AuthorView(
				au.id, au.firstName, ch.id, ch.title
			)
			FROM Author au LEFT OUTER JOIN Chapter ch ON au.id = ch.book.author.id
			""")
	List<AuthorView> findAllAuthorAndChapters();

}
