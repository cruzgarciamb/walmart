package com.walmart.library.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.library.models.entity.Book;
import com.walmart.library.service.IBookService;
import com.walmart.library.utils.CustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired IBookService service;
	
	@GetMapping
	@Operation(summary = "All Books", description = "To find all books records")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "BOOKS FOUND")
	})
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(service.getAll());
	}
	
	@GetMapping("/isbn/{isbn}")
	@Operation(summary = "ById Book", description = "To find book by id record")
	@Parameter(name = "isbn", required = true, description = "isbn book identifier", example = "9781939438638")
	@ApiResponses(value = {
			@ApiResponse (
					responseCode = "204",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "NO DATA FOUND"),
			@ApiResponse(
					responseCode = "200",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "BOOK FOUND")
	})
	public ResponseEntity<?> getByIsbn(@PathVariable String isbn) {
		Optional<Book> oBook = service.getByIsbn(isbn);
		if (!oBook.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(service.getByIsbn(isbn));
	}
	
	@PostMapping
	@Operation(summary = "Save Book", description = "To save book record")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "BOOK CREATED")
	})
	public ResponseEntity<?> post(@RequestBody Book book) {
		Optional<Book> oBook = service.getByIsbn(book.getIsbn());
		if(oBook.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body("ISBN ha sido registrado anteriormente");
		}
		Book bookDb = null;
		try {
			bookDb = service.save(book);
		} catch (CustomException customEx) {
			System.err.println(customEx.getMessage());
			return ResponseEntity.badRequest().body(customEx.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDb);
	}
	
	@GetMapping("/author-name/{authorName}")
	@Operation(summary = "By Author-name Books", description = "To find books records by author name")
	@Parameter(name = "authorName", required = true, description = "book author", example = "Jon")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "BOOKS FOUND")
	})
	public ResponseEntity<?> getByAuthorName(@PathVariable String authorName) {
		return ResponseEntity.ok().body(service.getByAuthorName(authorName));
	}
	
	@GetMapping("/publication-date/{publicationDate}")
	@Operation(summary = "By pulication-date Books", description = "To find books before to publication-date parameter")
	@Parameter(name = "publicationDate", required = true, description = "book publication date", example = "2023-10-25")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = { @Content(schema = @Schema(implementation = Book.class), mediaType = MediaType.APPLICATION_JSON_VALUE) },
					description = "BOOK FOUND")
	})
	public ResponseEntity<?> getByPublicationDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date publicationDate) {
		return ResponseEntity.ok().body(service.getByPublicationDate(publicationDate));
	}

}
