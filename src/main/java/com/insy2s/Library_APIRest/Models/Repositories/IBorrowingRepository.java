package com.insy2s.Library_APIRest.Models.Repositories;

import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBorrowingRepository extends JpaRepository<Borrowing, Long> {

    @Query("select b from Borrowing b where b.user.id = ?1 and b.returnDate is null")
    Optional<List<Borrowing>> findBorrowingByUserId(Long userId);

    List<Borrowing> findBorrowingByBookId(Long id);

    boolean existsByBookId(Long bookId);


}
