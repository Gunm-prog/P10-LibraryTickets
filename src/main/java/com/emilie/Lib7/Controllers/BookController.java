package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Dtos.BookDto;

import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Services.contract.BookService;

import com.emilie.Lib7.Services.contract.LibraryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private BookDto bookDto;
    private AuthorDto authorDto;


    @Autowired
    public BookController(BookService bookService,
                          LibraryService libraryService){
        this.bookService=bookService;
    }

    @ApiOperation( value="Retrieve the booklist which is registered in database" )
    @GetMapping("/catalog")
    public List<BookDto> findAll(){
        return this.bookService.findAll();
    }

    @ApiOperation( value="Retrieve a book by its title, if it is registered in database" )
    @GetMapping("/title/{title }")
    public BookDto findByTitle(@PathVariable String title) throws BookNotFoundException {
        /*bookDto.setTitle( title );*/
        return this.bookService.findByTitle( title);
    }

   @ApiOperation(value="Retrieve a booklist by author's name, if it is registered")
    @GetMapping("/author/{author}")
    public BookDto findByAuthor(@PathVariable(value="author") Author author){
        bookDto.setAuthorDto( authorDto );
        return this.bookService.findByAuthor(author);
    }

}
