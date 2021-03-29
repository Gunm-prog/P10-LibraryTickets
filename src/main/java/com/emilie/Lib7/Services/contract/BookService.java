package com.emilie.Lib7.Services.contract;



import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Entities.Author;

import java.util.List;


public interface BookService {





   BookDto findById(Long id);

    BookDto findByTitle(String title);

    BookDto findByAuthor(Author author);

    List<BookDto> findAll();
}
