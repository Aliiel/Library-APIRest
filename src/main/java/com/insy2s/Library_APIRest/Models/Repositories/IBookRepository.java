package com.insy2s.Library_APIRest.Models.Repositories;

import com.insy2s.Library_APIRest.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleAndAuthor(String title, String author);
}
