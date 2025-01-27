package com.insy2s.Library_APIRest.Controllers;

import com.insy2s.Library_APIRest.Exceptions.*;
import com.insy2s.Library_APIRest.Exceptions.NullPointerException;
import com.insy2s.Library_APIRest.Models.DTO.BorrowingDTO;
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


    // endpoint pour lister tous les emprunts enregistrés
    @GetMapping
    public ResponseEntity<List<BorrowingDTO>> getAllBorrowings() {
        List<BorrowingDTO> borrowingsDTO = borrowingService.getAllBorrowings();
        return ResponseEntity.ok(borrowingsDTO);
    }


    // endpoint pour lister tous les emprunts en cours pour un utilisateur a partir de son id
    @GetMapping("/{userId}")
    public ResponseEntity<?> getBorrowingsById
            (@PathVariable("userId") Long userId) {

        try {
            List<BorrowingDTO> borrowingsDTO = borrowingService.getBorrowingsByUserId(userId);
            return ResponseEntity.ok(borrowingsDTO);

        } catch (NoBorrowingsFoundException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // endpoint pour emprunter un livre
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


    // endpoint pour rendre un livre emprunté
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
