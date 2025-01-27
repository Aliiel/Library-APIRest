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


    // endpoint pour afficher tous les livres enregistrés
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }


    // endpoint pour afficher un livre à partir de son id
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);

        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // endpoint pour lister les livres selon leur disponibilité :
    // - http://localhost:8080/books/isAvailable?isAvailable=false : liste les livres non dispo
    // - http://localhost:8080/books/isAvailable?isAvailable=true : liste les livres dispo
    @GetMapping("/isAvailable")
    public ResponseEntity<List<Book>> getBooksByAvailability
            (@RequestParam("isAvailable") boolean isAvailable) {
        List<Book> books = bookService.getBooksAvailableOrNot(isAvailable);
        return ResponseEntity.ok(books);
    }


    // endpoint pour enregistrer un livre (préciser seulement le titre et l'auteur dans le body)
    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.ok("Livre créé !");

        } catch (BookAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // endpoint pour modifier un livre à partir de son id (possibilité de modifier qu'un seul attribut à préciser dans le body)
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


    // endpoint pour supprimer un livre à partir de son id
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
