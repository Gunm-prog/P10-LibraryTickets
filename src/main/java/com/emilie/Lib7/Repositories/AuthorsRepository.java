package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {


    Optional<Author> findById(Long id);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);
    List<Author> findAll();
}
