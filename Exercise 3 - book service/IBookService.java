package com.walmart.library.service;

import java.util.Optional;

import com.walmart.library.models.entity.Book;

public interface IBookService {
	
	Iterable<Book> getAll();
	
	Optional<Book> getByIsbn(String isbn);
	
	Book save(Book book);

}
