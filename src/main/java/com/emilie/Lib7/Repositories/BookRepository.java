package com.emilie.Lib7.Repositories;


import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

/*    @Query(value="SELECT DISTINCT s FROM Spot s " +
            "JOIN Area area ON s.id = area.spot.id " +
            "JOIN Route r ON area.id = r.area.id " +
            "JOIN Pitch p ON r.id = p.route.id " +
            "WHERE p.quotation = :quotation " +
            "AND " +
            " (s.name LIKE '%' || :keyword || '%' OR s.description LIKE '%' || :keyword || '%')")
    public List<Spot> searchByQuotationAndKeyword(@Param("quotation") String quotation, @Param("keyword") String keyword);*/


    @Query(value = "SELECT DISTINCT book FROM Book book " +
            "JOIN Copy copy ON book.id = copy.book.id " +
            "JOIN copy.library library " +
            "WHERE library.id = :libraryId ")
    List<Book> findBooksByLibraryId( @Param( "libraryId" ) Long id);

    List<Book> findAll();
    Optional<Book> findByTitle(String title);

    Optional<Book> findByAuthor(Author author);

    Optional<Book> findById(Long id);





    /* Optional<Book> findByAuthor(Author author);*/

}