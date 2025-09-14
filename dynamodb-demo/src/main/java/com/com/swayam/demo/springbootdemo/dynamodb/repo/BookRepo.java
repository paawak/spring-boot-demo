package com.com.swayam.demo.springbootdemo.dynamodb.repo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.com.swayam.demo.springbootdemo.dynamodb.model.Book;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Repository
public class BookRepo {

    public void insertBook(DynamoDbTable<Book> bookTable) {
	Book newBook = new Book();
	newBook.setId("1111");
	newBook.setPublishedOn(LocalDateTime.of(2025, 7, 8, 13, 10));
	newBook.setTitle("AAAA");
	newBook.setDescription("BBBB");
	newBook.setAuthorId("ccc");

	bookTable.putItem(newBook);
    }

    public void updateBookPublishedOn(DynamoDbTable<Book> bookTable, Book existingBook, LocalDateTime newDate) {
	existingBook.setPublishedOn(newDate);

	bookTable.updateItem(existingBook);
    }

}
