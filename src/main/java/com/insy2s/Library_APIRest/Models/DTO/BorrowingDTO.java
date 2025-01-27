package com.insy2s.Library_APIRest.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowingDTO {

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private BookDTO bookDTO;
    private UserDTO userDTO;
}
