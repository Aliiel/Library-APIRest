package com.insy2s.Library_APIRest.Models.Repositories;

import com.insy2s.Library_APIRest.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    boolean existsByNameAndEmail(String username, String email);
}
