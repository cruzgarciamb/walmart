package com.walmart.library.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.walmart.library.models.entity.Book;

@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {

	Iterable<Book> findAll();
	
	Optional<Book> findByIsbn(String isbn);
	
	Book save(Book book);
	
	List<Book> findByAuthorNameContainingIgnoreCase(String name);
	
	Iterable<Book> findByPublicationDateGreaterThan(Date publicationDate);
	
}
