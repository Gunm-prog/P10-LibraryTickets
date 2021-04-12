package com.emilie.Lib7.Services.contract;



import com.emilie.Lib7.Exceptions.BookAlreadyExistException;
import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;

import java.util.List;


public interface BookService {





   BookDto findById(Long id) throws BookNotFoundException;

   BookDto save(BookDto bookDto) throws BookAlreadyExistException;

   BookDto update(BookDto bookDto);

   boolean deleteById(Long id) throws BookNotFoundException;

    BookDto findByTitle(String title);

    BookDto findByAuthor(Author author);

    List<BookDto> findAll();
}