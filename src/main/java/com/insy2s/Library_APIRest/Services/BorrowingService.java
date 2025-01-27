package com.insy2s.Library_APIRest.Services;

import com.insy2s.Library_APIRest.Exceptions.BookUnavailableException;
import com.insy2s.Library_APIRest.Exceptions.BorrowingLimitExceededException;
import com.insy2s.Library_APIRest.Exceptions.NoBorrowingsFoundException;
import com.insy2s.Library_APIRest.Exceptions.UserNotFoundException;
import com.insy2s.Library_APIRest.Models.Entities.Book;
import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import com.insy2s.Library_APIRest.Models.Entities.User;
import com.insy2s.Library_APIRest.Models.Repositories.IBorrowingRepository;
import com.insy2s.Library_APIRest.Models.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    private final BookService bookService;
    private final UserService userService;
    private final IBorrowingRepository borrowingRepository;
    private final IUserRepository userRepository;

    public BorrowingService(
            BookService bookService,
            UserService userService,
            IBorrowingRepository borrowingRepository,
            IUserRepository userRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
    }


    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }


    public void borrowBook(Long bookId, Long userId) {

        LocalDate borrowDate = LocalDate.now();
        Book book = bookService.getBookById(bookId);

        if (!book.getIsAvailable()) {

            System.out.println("Le livre n'est pas disponible, exception");

            throw new BookUnavailableException
                    ("Le livre que vous souhaitez emprunter est déjà en cours d'emprunt");

        } else if (!canUserBorrowBook(userId)) {

            throw new BorrowingLimitExceededException
                    ("Vous avez déjà 3 emprunts en cours, vous ne pouvez pas emprunter de livre supplémentaire");

        } else {

            book.setIsAvailable(false);
            User user = userService.getUserById(userId);

            Borrowing borrowing = new Borrowing();
            borrowing.setBorrowDate(borrowDate);
            borrowing.setBook(book);
            borrowing.setUser(user);
            borrowingRepository.save(borrowing);
        }
    }


    public void returnBook(Long bookId) {

        if (!borrowingRepository.existsByBookId(bookId)) {
            throw new NullPointerException("Il n'y a pas d'emprunt enregistré pour ce livre");
        }

        LocalDate returnDate = LocalDate.now();
        Book book = bookService.getBookById(bookId);
        List<Borrowing> borrowings = borrowingRepository.findBorrowingByBookId(bookId);

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getReturnDate() == null) {
                borrowing.setReturnDate(returnDate);
                book.setIsAvailable(true);
                borrowingRepository.save(borrowing);
            }
        }
    }


    public List<Borrowing> getBorrowingsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("L'utilisateur avec l'ID " + userId + " n'existe pas");
        }
        return borrowingRepository.findBorrowingByUserId(userId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NoBorrowingsFoundException("L'utilisateur n'a pas d'emprunts en cours"));
    }


    public boolean canUserBorrowBook(Long userId) {

        Optional<List<Borrowing>> borrowings = borrowingRepository.findBorrowingByUserId(userId);
        if (borrowings.get().size() >= 3) {
            return false;
        }
        return true;
    }
}
