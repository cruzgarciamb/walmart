package com.walmart.library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.library.models.entity.Book;
import com.walmart.library.repository.IBookRepository;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired private IBookRepository repository;
	
	@Override
	public Iterable<Book> getAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Book> getByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}

	@Override
	public Book save(Book book) {
		return repository.save(book);
	}

}
