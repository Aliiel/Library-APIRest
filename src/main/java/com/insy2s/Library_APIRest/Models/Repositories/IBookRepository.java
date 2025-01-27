package com.insy2s.Library_APIRest.Models.Repositories;

import com.insy2s.Library_APIRest.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleAndAuthor(String title, String author);

    @Query("select b from Book b where b.isAvailable = ?1")
    List<Book> findByIsAvailable(Boolean isAvailable);
}
