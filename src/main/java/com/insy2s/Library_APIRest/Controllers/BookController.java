package com.insy2s.Library_APIRest.Controllers;

import com.insy2s.Library_APIRest.Exceptions.BookAlreadyExistsException;
import com.insy2s.Library_APIRest.Exceptions.BookNotFoundException;
import com.insy2s.Library_APIRest.Models.Entities.Book;
import com.insy2s.Library_APIRest.Services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);

        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/isAvailable")
    public ResponseEntity<List<Book>> getBooksByAvailability
            (@RequestParam("isAvailable") boolean isAvailable) {
        List<Book> books = bookService.getBooksAvailableOrNot(isAvailable);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.ok("Livre créé !");

        } catch (BookAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody Book bookUpdated
    ) {
        try {
            Book updatedBook = bookService.updateBook(id, bookUpdated);
            return ResponseEntity.ok(updatedBook);

        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Livre supprimé");

        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
