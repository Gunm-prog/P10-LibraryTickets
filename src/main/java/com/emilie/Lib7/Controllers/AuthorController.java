package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorsService authorsService;
    private Author authorDto;


    @Autowired
    public AuthorController(AuthorsService authorsService, BookService bookService){

        this.authorsService=authorsService;
    }

    @ApiOperation( value="Retrieve userlist from database" )
    @GetMapping("/list")
    public List<AuthorDto> findAll(){
        return this.authorsService.findAll();
    }

    @ApiOperation( value="Retrieve an author by its id if existing in database" )
    @GetMapping("/{id}")
    public AuthorDto getId(@PathVariable(value="id") Long id){
        return this.authorsService.findById( id );
    }

    @GetMapping("/{first-name}")
    public AuthorDto getFirstName(@PathVariable(value="first-name") String firstName){
        return this.authorsService.findByFirstName(firstName);
    }

    @GetMapping("/last-name")
    public AuthorDto getLastName(String lastName){
        return this.authorsService.findByLastName( lastName );
    }

}
