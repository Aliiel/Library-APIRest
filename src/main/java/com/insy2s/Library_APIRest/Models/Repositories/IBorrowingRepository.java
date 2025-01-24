package com.insy2s.Library_APIRest.Models.Repositories;

import com.insy2s.Library_APIRest.Models.Entities.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBorrowingRepository extends JpaRepository<Borrowing, Long> {
}
