package com.insy2s.Library_APIRest.Models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    @NotNull(message = "Vous devez saisir le titre du livre")
    private String title;

    @NotNull(message = "Vous devez saisir l'auteur du livre")
    private String author;

    private Boolean isAvailable = true;
}
