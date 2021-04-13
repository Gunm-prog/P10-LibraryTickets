package com.emilie.Lib7.Repositories;


import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();
    Optional<Book> findByTitle(String title);

    Optional<Book> findByAuthor(Author author);

    Optional<Book> findById(Long id);





    /* Optional<Book> findByAuthor(Author author);*/

}