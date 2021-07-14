package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.BookAlreadyExistException;
import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Services.contract.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

   /* private final FeignProxy feignProxy;*/

    private final BookService bookService;


    /*@Autowired
    public BookController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }*/

    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;

    }


    @ApiOperation( value="Retrieve a book by id, if registered in database" )
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable(value="id")Long id) throws BookNotFoundException{
        BookDto bookDto = bookService.findById( id );
        return new ResponseEntity<BookDto>( bookDto, HttpStatus.OK );
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
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto, @PathVariable(value="id") Long id){
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
    @GetMapping("/title")
    public ResponseEntity<BookDto> findByTitle(@RequestParam String title) throws BookNotFoundException, UnsupportedEncodingException {
        BookDto bookDto = bookService.findByTitle( title );
        /*bookDto.setTitle( title );*/
        return new ResponseEntity<BookDto>(bookDto,HttpStatus.OK);
    }



   /*@ApiOperation(value="Retrieve a booklist by author's name, if it is registered")
    @GetMapping("/author/{author}")
    public BookDto findByAuthor(@PathVariable(value="author") Author author){
        bookDto.setAuthorDto( authorDto );
        return this.bookService.findByAuthor(author);
    }*/

}
