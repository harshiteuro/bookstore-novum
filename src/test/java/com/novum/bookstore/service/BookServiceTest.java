package com.novum.bookstore.service;

import com.novum.bookstore.models.BookModel;
import com.novum.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        // Initialize Mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_Success() {
        // mock data
        BookModel book1 = new BookModel(1L, "Book One", "Author One", 10.99);
        BookModel book2 = new BookModel(2L, "Book Two", "Author Two", 15.99);
        List<BookModel> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        // mthod invocation
        List<BookModel> result = bookService.getAllBooks();

        // verify
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll(); // Verify method call
    }

    @Test
    void testGetBookById_Found_Success() {
        // mokc data
        BookModel book = new BookModel(1L, "Book One", "Author One", 10.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // method invocation
        Optional<BookModel> result = bookService.getBookById(1L);

        // verify
        assertTrue(result.isPresent());
        assertEquals("Book One", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound_Failure() {
        // mock data
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // method call
        Optional<BookModel> result = bookService.getBookById(1L);

        // verify
        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBook_Success() {
        // mock data
        BookModel book = new BookModel(1L, "New Book", "New Author", 20.99);
        when(bookRepository.save(book)).thenReturn(book);

        // method call
        BookModel result = bookService.createBook(book);

        // verify
        assertNotNull(result);
        assertEquals("New Book", result.getTitle());
    }

    @Test
    void testUpdateBook_Found_Success() {
        // mock data
        BookModel existingBook = new BookModel(1L, "Old Title", "Old Author", 15.00);
        BookModel updatedDetails = new BookModel(1L, "Updated Title", "Updated Author", 18.99);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedDetails);

        // method call
        BookModel result = bookService.updateBook(1L, updatedDetails);

        // varify
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals(18.99, result.getPrice());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testUpdateBook_NotFound_Failure() {
        // mock data
        BookModel updatedDetails = new BookModel(1L, "Updated Title", "Updated Author", 18.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // method call
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(1L, updatedDetails);
        });

        // verify
        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testDeleteBook_Success() {
        // mock data
        doNothing().when(bookRepository).deleteById(1L);

        // nmethod call
        bookService.deleteBook(1L);

        // verify
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
