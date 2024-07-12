package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/test")
	public String myFun() {
		return "Backend Started";
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> book = bookService.getBookById(id);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book createdBook = bookService.saveOrUpdateBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		if (!bookService.getBookById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		book.setId(id);
		Book updatedBook = bookService.saveOrUpdateBook(book);
		return ResponseEntity.ok(updatedBook);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		if (!bookService.getBookById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
