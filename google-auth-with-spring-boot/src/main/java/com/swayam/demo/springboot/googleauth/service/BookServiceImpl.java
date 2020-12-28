package com.swayam.demo.springboot.googleauth.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.googleauth.model.old.Book;
import com.swayam.demo.springboot.googleauth.repo.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
	this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
	return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
	return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Book getBook(long bookId) {
	return bookRepository.findById(bookId).get();
    }

}
