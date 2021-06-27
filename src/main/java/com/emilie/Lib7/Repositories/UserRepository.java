package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
   /*User user save(User user);*/

     Optional<User> findByLastName(String lastName);

    Optional<User> findByUsername(String username);

    /*org.springframework.security.core.userdetails.User findByUsername(String username);*/

    /*   Set<LoanDto> findLoansByUser(Long userId);*/





}
