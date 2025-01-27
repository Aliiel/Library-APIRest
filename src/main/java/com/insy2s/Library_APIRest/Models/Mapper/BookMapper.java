package com.insy2s.Library_APIRest.Models.Mapper;

import com.insy2s.Library_APIRest.Models.DTO.BookDTO;
import com.insy2s.Library_APIRest.Models.Entities.Book;

public class BookMapper {

    // méthode permettant de convertir mon objet DTO en entité book
    public static BookDTO toBookDTO(Book book) {

        if (book == null){
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsAvailable(book.getIsAvailable());

        return bookDTO;
    }


    // méthode permettant de convertir une entité book en un objet BookDTO
    public static Book toBook(BookDTO bookDTO) {

        if (bookDTO == null){
            return null;
        }

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsAvailable(bookDTO.getIsAvailable());

        return book;
    }
}
