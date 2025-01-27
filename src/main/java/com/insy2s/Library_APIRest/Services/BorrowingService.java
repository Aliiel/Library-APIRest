package com.insy2s.Library_APIRest.Services;

import com.insy2s.Library_APIRest.Exceptions.*;
import com.insy2s.Library_APIRest.Models.DTO.BookDTO;
import com.insy2s.Library_APIRest.Models.DTO.BorrowingDTO;
import com.insy2s.Library_APIRest.Models.DTO.UserDTO;
import com.insy2s.Library_APIRest.Models.Entities.Book;
import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import com.insy2s.Library_APIRest.Models.Entities.User;
import com.insy2s.Library_APIRest.Models.Mapper.BookMapper;
import com.insy2s.Library_APIRest.Models.Mapper.BorrowingMapper;
import com.insy2s.Library_APIRest.Models.Mapper.UserMapper;
import com.insy2s.Library_APIRest.Models.Repositories.IBookRepository;
import com.insy2s.Library_APIRest.Models.Repositories.IBorrowingRepository;
import com.insy2s.Library_APIRest.Models.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.lang.NullPointerException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    private final BookService bookService;
    private final UserService userService;
    private final IBorrowingRepository borrowingRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    public BorrowingService(
            BookService bookService,
            UserService userService,
            IBorrowingRepository borrowingRepository,
            IUserRepository userRepository,
            IBookRepository bookRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    // méthode pour récupérer tous les emprunts enregistrés en bdd
    public List<BorrowingDTO> getAllBorrowings() {

        return borrowingRepository.findAll()
                .stream()
                .map(BorrowingMapper::toBorrowingDTO)
                .toList();
    }


    // méthode pour emprunter un livre
    public void borrowBook(Long bookId, Long userId) {

        // Détermination de la date d'emprunt à l'instant présent
        LocalDate borrowDate = LocalDate.now();

        // récupération du livre concerné à partir de son id passé en paramètre
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Le livre avec l'id " + bookId + " n'existe pas"));

        // vérification de la disposability du livre
        if (!book.getIsAvailable()) {

            throw new BookUnavailableException
                    ("Le livre que vous souhaitez emprunter est déjà en cours d'emprunt");

            // vérification du nombre d'emprunts en cours pour l'utilisateur concerné
        } else if (!canUserBorrowBook(userId)) {

            throw new BorrowingLimitExceededException
                    ("Vous avez déjà 3 emprunts en cours, vous ne pouvez pas emprunter de livre supplémentaire");

        } else {

            // maj de la dispo du livre
            book.setIsAvailable(false);

            // récupération de l'utilisateur qui effectue l'emprunt a partir de son id
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'id " + userId + " n'existe pas"));

            // création d'une nouvelle entrée d'emprunt reprenant la date d'emprunt, le livre emprunté, et l'utilisateur qui effectue l'emprunt
            Borrowing borrowing = new Borrowing();
            borrowing.setBorrowDate(borrowDate);
            borrowing.setBook(book);
            borrowing.setUser(user);
            borrowingRepository.save(borrowing);
        }
    }


    // méthode pour rendre un livre
    public void returnBook(Long bookId) {

        // vérification que le livre a rendre est dans la liste des emprunts
        if (!borrowingRepository.existsByBookId(bookId)) {
            throw new NullPointerException("Il n'y a pas d'emprunt enregistré pour ce livre");
        }

        // Détermination de la date de retour à l'instant présent
        LocalDate returnDate = LocalDate.now();

        // récupération du livre concerné a partir de son id
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Le livre avec l'id " + bookId + " n'existe pas"));

        // récupération de la liste des emprunts enregistrés pour ce livre
        List<Borrowing> borrowings = borrowingRepository.findBorrowingByBookId(bookId);

        // récupération enregistrements non rendus présents dans la liste des emprunts du livre
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getReturnDate() == null) {
                borrowing.setReturnDate(returnDate);
                book.setIsAvailable(true);
                borrowingRepository.save(borrowing);
            }
        }
    }


    // méthode pour récupérer la liste des livres empruntés pour un utilisateur à partir de son id
    public List<BorrowingDTO> getBorrowingsByUserId(Long userId) {
        // vérification de l'existence de l'utilisateur concerné
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("L'utilisateur avec l'ID " + userId + " n'existe pas");
        }

        // retourne la liste des livres en cours d'emprunt et lève l'exception si la liste est vide
        return borrowingRepository.findBorrowingByUserId(userId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NoBorrowingsFoundException("L'utilisateur n'a pas d'emprunts en cours"))
                .stream()
                .map(BorrowingMapper::toBorrowingDTO)
                .toList();
    }


    // méthode pour vérifier si un utilisateur peut effectuer un nouvel emprunt ou non
    public boolean canUserBorrowBook(Long userId) {

        // récupération des emprunts en cours enregistrés pour un utilisateur
        Optional<List<Borrowing>> borrowings = borrowingRepository.findBorrowingByUserId(userId);

        // si la liste des emprunts contient 3 enregistrements ou plus, retourne false
        if (borrowings.get().size() >= 3) {
            return false;
        }
        return true;
    }
}
