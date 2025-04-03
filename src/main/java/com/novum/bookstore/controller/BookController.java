package com.novum.bookstore.controller;

import com.novum.bookstore.models.BookModel;
import com.novum.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookstore/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieve all books from the bookstore.
     *
     * @return ResponseEntity containing a list of books or No Content status
     */
    @GetMapping
    public List<BookModel> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Retrieve a specific book by its ID.
     *
     * @param id Book ID
     * @return ResponseEntity with book data or Not Found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new book in the bookstore.
     *
     * @param book Book details
     * @return ResponseEntity with the created book
     */
    @PostMapping
    public ResponseEntity<BookModel> createBook(@RequestBody BookModel book) {
        BookModel createdBook = bookService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    /**
     * Update an existing book.
     *
     * @param id          Book ID
     * @param bookDetails Updated book details
     * @return ResponseEntity with updated book or Not Found if the book does not
     *         exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody BookModel bookDetails) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a book by ID.
     *
     * @param id Book ID
     * @return ResponseEntity with Not found or No Content status if deleted
     *         successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
