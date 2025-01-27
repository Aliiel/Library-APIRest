package com.insy2s.Library_APIRest.Controllers;

import com.insy2s.Library_APIRest.Exceptions.*;
import com.insy2s.Library_APIRest.Exceptions.NullPointerException;
import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import com.insy2s.Library_APIRest.Services.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }


    @GetMapping
    public ResponseEntity<List<Borrowing>> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingService.getAllBorrowings();
        return ResponseEntity.ok(borrowings);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getBorrowingsById
            (@PathVariable("userId") Long userId) {

        try {
            List<Borrowing> borrowings = borrowingService.getBorrowingsByUserId(userId);
            return ResponseEntity.ok(borrowings);

        } catch (NoBorrowingsFoundException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{bookId}/{userId}")
    public ResponseEntity<?> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {

        try {
            borrowingService.borrowBook(bookId, userId);
            return ResponseEntity.ok("Le livre a bien été emprunté");

        } catch (BookNotFoundException | UserNotFoundException | BookUnavailableException |
                 BorrowingLimitExceededException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/{bookId}")
    public ResponseEntity<?> returnBook(
            @PathVariable Long bookId
    ) {
        try {
            borrowingService.returnBook(bookId);
            return ResponseEntity.ok("Livre rendu");

        } catch (BookNotFoundException | UserNotFoundException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
