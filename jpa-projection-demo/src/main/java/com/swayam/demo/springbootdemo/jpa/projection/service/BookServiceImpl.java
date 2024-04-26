package com.swayam.demo.springbootdemo.jpa.projection.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.jpa.projection.dao.BookDao;
import com.swayam.demo.springbootdemo.jpa.projection.dao.BookDao.BookView;
import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

@Service
public class BookServiceImpl implements BookService {

	private final BookDao bookRepo;

	public BookServiceImpl(BookDao bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public List<Book> getBooks() {
		return bookRepo.findAll();
	}

	@Override
	public List<BookView> getBooksForView() {
		return bookRepo.findAllByIdNotNull();
	}

	@Override
	public Book getBook(Long bookId) {
		return bookRepo.getReferenceById(bookId);
	}

	@Override
	public Book saveOrUpdate(Book book) {
		return bookRepo.save(book);
	}

}
