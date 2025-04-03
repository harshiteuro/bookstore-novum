package com.novum.bookstore.constroller;

import com.novum.bookstore.models.BookModel;
import com.novum.bookstore.service.BookService;
import com.novum.bookstore.controller.BookController;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_Success() {
        // mock data
        BookModel book1 = new BookModel(1L, "Book One", "Author One", 10.99);
        BookModel book2 = new BookModel(2L, "Book Two", "Author Two", 15.99);

        List<BookModel> books = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(books);

        // getAllBooks called
        List<BookModel> result = bookController.getAllBooks();

        // verify
        assertEquals(books.size(), result.size());
        assertEquals("Book One", result.get(0).getTitle());
        assertEquals("Book Two", result.get(1).getTitle());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_BookExists_Success() {
        // mock data
        BookModel book = new BookModel(1L, "Book One", "Author One", 10.99);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        // getBookById called
        ResponseEntity<BookModel> response = bookController.getBookById(1L);

        // verify
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Book One", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_BookNotFound_Failure() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        // getBookById called
        ResponseEntity<BookModel> response = bookController.getBookById(1L);

        // verify
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testCreateBook_Success() {
        // mock data
        BookModel book = new BookModel(1L, "New Book", "New Author", 12.99);
        when(bookService.createBook(any(BookModel.class))).thenReturn(book);

        // create method called
        ResponseEntity<BookModel> createdBook = bookController.createBook(book);

        // verify
        assertNotNull(createdBook);
        assertEquals(Response.SC_OK, createdBook.getStatusCode().value());
        verify(bookService, times(1)).createBook(book);
    }

    @Test
    void testUpdateBook_Success() {
        // mock data
        BookModel bookDetails = new BookModel(1L, "Updated Book", "Updated Author", 14.99);
        when(bookService.updateBook(eq(1L), any(BookModel.class))).thenReturn(bookDetails);

        // update called
        ResponseEntity<BookModel> response = bookController.updateBook(1L, bookDetails);

        // verify
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Book", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook(1L, bookDetails);
    }

    @Test
    void testUpdateBook_NotFound_Failure() {
        when(bookService.updateBook(eq(1L), any(BookModel.class))).thenThrow(new RuntimeException("Book not found"));

        // update called
        ResponseEntity<BookModel> response = bookController.updateBook(1L, new BookModel());

        // verify
        assertEquals(404, response.getStatusCode().value());
        verify(bookService, times(1)).updateBook(eq(1L), any(BookModel.class));
    }

    @Test
    void testDeleteBook_Success() {
        // mock
        BookModel book = new BookModel(1L, "Test Book", "Test Author", 10.99);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        // delete is called
        doNothing().when(bookService).deleteBook(1L);
        ResponseEntity<Void> response = bookController.deleteBook(1L);

        // verify
        assertEquals(204, response.getStatusCode().value());
        verify(bookService, times(1)).deleteBook(1L);
    }

}