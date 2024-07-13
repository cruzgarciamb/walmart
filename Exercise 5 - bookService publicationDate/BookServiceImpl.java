package com.walmart.library.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.library.models.entity.Book;
import com.walmart.library.repository.IBookRepository;
import com.walmart.library.utils.CustomException;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired private IBookRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Book> getAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Book> getByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}

	@Override
	@Transactional
	public Book save(Book book) {
		if(book.getPublicationDate().after(new Date())) {
			CustomException customEx = new CustomException("Publication date: la fecha de publicación inválida");
			throw customEx;
		}
		return repository.save(book);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> getByAuthorName(String name) {
		return repository.findByAuthorNameContainingIgnoreCase(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Book> getByPublicationDate(Date publicationDate) {
		return repository.findByPublicationDateGreaterThan(publicationDate);
	}

}
