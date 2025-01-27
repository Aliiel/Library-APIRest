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


    // méthode pour récupérer tous les livres depuis la bdd
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    // méthode pour récupérer les livres selon leur disponibilité
    public List<Book> getBooksAvailableOrNot(boolean isAvailable) {
        return bookRepository.findByIsAvailable(isAvailable);
    }


    // méthode pour récupérer un livre en bdd à partir de son id
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookNotFoundException("Le livre avec l'id " + id + " n'existe pas"));
    }


    // méthode pour ajouter un nouveau livre en bdd
    public void addBook(Book book) {
        // vérification de la présence d'un livre en bdd à partir de son nom et de son auteur
        boolean bookExists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (bookExists) {
            throw new BookAlreadyExistsException("Un livre avec ce titre et cet auteur existe déjà.");
        }
        bookRepository.save(book);
    }


    // méthode pour modifier un livre
    public Book updateBook(Long id, Book bookUpdated) {

        // vérification et récupération du livre à partir de son id
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Le livre avec l'id" + id + " n'existe pas"));


        // modification des attributs passés en paramètre dans le body bookUpdated
        // si un attribut n'est pas passé en paramètre, l'objet conserve sa valeur initiale
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


    // méthode pour supprimer un livre
    public void deleteBook(Long id) {

        Optional<Book> existingBook = bookRepository.findById(id);
        existingBook.ifPresentOrElse(
                bookRepository::delete,
                () -> {
                    throw new BookNotFoundException("Le livre avec l'id" + id + " n'existe pas");
                }
        );
    }
}

