package com.insy2s.Library_APIRest.Models.Mapper;

import com.insy2s.Library_APIRest.Models.DTO.BookDTO;
import com.insy2s.Library_APIRest.Models.DTO.BorrowingDTO;
import com.insy2s.Library_APIRest.Models.DTO.UserDTO;
import com.insy2s.Library_APIRest.Models.Entities.Book;
import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import com.insy2s.Library_APIRest.Models.Entities.User;

public class BorrowingMapper {

    // méthode permettant de convertir une entité en objet DTO
    public static BorrowingDTO toBorrowingDTO(Borrowing borrowing) {

        if (borrowing == null) {
            return null;
        }

        BorrowingDTO borrowingDTO = new BorrowingDTO();
        borrowingDTO.setBorrowDate(borrowing.getBorrowDate());
        borrowingDTO.setReturnDate(borrowing.getReturnDate());

        UserDTO userDTO = UserMapper.toUserDTO(borrowing.getUser());
        borrowingDTO.setUserDTO(userDTO);

        BookDTO bookDTO = BookMapper.toBookDTO(borrowing.getBook());
        borrowingDTO.setBookDTO(bookDTO);

        return borrowingDTO;
    }


    // méthode permettant de convertir un objet DTO en entité
    public static Borrowing toBorrowing(BorrowingDTO borrowingDTO) {
        if (borrowingDTO == null) {
            return null;
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowDate(borrowingDTO.getBorrowDate());
        borrowing.setReturnDate(borrowingDTO.getReturnDate());

        User user = UserMapper.toUser(borrowingDTO.getUserDTO());
        borrowing.setUser(user);

        Book book = BookMapper.toBook(borrowingDTO.getBookDTO());
        borrowing.setBook(book);

        return borrowing;
    }
}
