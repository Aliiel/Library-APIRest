package com.insy2s.Library_APIRest.Services;

import com.insy2s.Library_APIRest.Exceptions.BookAlreadyExistsException;
import com.insy2s.Library_APIRest.Exceptions.BookNotFoundException;
import com.insy2s.Library_APIRest.Models.Entities.Book;
import com.insy2s.Library_APIRest.Models.Repositories.IBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }


    public void addBook(Book book) {
        boolean bookExists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (bookExists) {
            throw new BookAlreadyExistsException("Un livre avec ce titre et cet auteur existe déjà.");
        }
        bookRepository.save(book);
    }


    public Book updateBook(Long id, Book bookUpdated) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));


        if (bookUpdated.getTitle() != null) {
            existingBook.setTitle(bookUpdated.getTitle());
        }

        if (bookUpdated.getAuthor() != null) {
            existingBook.setAuthor(bookUpdated.getAuthor());
        }

        if (bookUpdated.getIsAvailable() != null) {
            existingBook.setIsAvailable(bookUpdated.getIsAvailable());
        }

        return bookRepository.save(existingBook);
    }


    public void deleteBook(Long id) {

        Optional<Book> existingBook = bookRepository.findById(id);
        existingBook.ifPresentOrElse(
                bookRepository::delete,
                () -> {
                    throw new BookNotFoundException("Book with ID " + id + " not found");
                }
        );
    }
}

