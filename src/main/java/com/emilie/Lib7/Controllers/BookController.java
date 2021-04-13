package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.BookAlreadyExistException;
import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Dtos.BookDto;

import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Services.contract.BookService;

import com.emilie.Lib7.Services.contract.LibraryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;

    }


    @ApiOperation( value="Retrieve a book by id, if registered in database" )
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable(value="id")Long id) throws BookNotFoundException{
        return this.bookService.findById( id );
    }


    @ApiOperation( value="Retrieve the booklist which is registered in database" )
    @GetMapping("/catalog")
    public List<BookDto> findAll(){
        return this.bookService.findAll();
    }


    @ApiOperation( value="Register a new book in database" )
    @PostMapping("/newBook")
    public ResponseEntity<String> save(@RequestBody BookDto bookDto) throws BookAlreadyExistException{
        bookService.save(bookDto);
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }


    @ApiOperation( value="update book (like new edition???) and save modifications in database" )
    @PutMapping("/updateBook")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.update( bookDto );
        return  new ResponseEntity<BookDto>(bookDto1, HttpStatus.OK);
    }


    @ApiOperation( value="delete a book from database by id" )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id) throws BookNotFoundException{
        if (bookService.deleteById(id)){
            return ResponseEntity.status( HttpStatus.OK ).build();
        }else {
            return ResponseEntity.status( 500 ).build();
        }
    }


    @ApiOperation( value="Retrieve a book by its title, if it is registered in database" )
    @GetMapping("/title/{title }")
    public BookDto findByTitle(@PathVariable String title) throws BookNotFoundException {
        /*bookDto.setTitle( title );*/
        return this.bookService.findByTitle( title);
    }



   /*@ApiOperation(value="Retrieve a booklist by author's name, if it is registered")
    @GetMapping("/author/{author}")
    public BookDto findByAuthor(@PathVariable(value="author") Author author){
        bookDto.setAuthorDto( authorDto );
        return this.bookService.findByAuthor(author);
    }*/

}
