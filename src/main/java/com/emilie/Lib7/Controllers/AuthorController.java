package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.AuthorAlreadyExistException;
import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorsService authorsService;
    private Author authorDto;


    @Autowired
    public AuthorController(AuthorsService authorsService){

        this.authorsService=authorsService;

    }

    @ApiOperation( value="Retrieve userlist from database" )
    @GetMapping("/list")
    public ResponseEntity<List<AuthorDto>> findAll(){
        List<AuthorDto> authorDtos = authorsService.findAll();
        return new ResponseEntity<List<AuthorDto>>(authorDtos, HttpStatus.OK  );
    }

    @ApiOperation( value="Retrieve an author by its id if existing in database" )
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> findById(@PathVariable(value="id") Long id){
        AuthorDto authorDto = authorsService.findById( id );
        return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK  );
    }


    @PostMapping("/newAuthor")
    public ResponseEntity<String> save(@RequestBody AuthorDto authorDto) throws AuthorAlreadyExistException{
        authorsService.save( authorDto );
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @PutMapping("/updateAuthor")
    public ResponseEntity<AuthorDto> update(@RequestBody AuthorDto authorDto){
        AuthorDto authorDto1 = authorsService.update( authorDto );
        return new ResponseEntity<AuthorDto>(authorDto1, HttpStatus.OK  );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id")Long id) throws AuthorNotFoundException{
        if (authorsService.deleteById(id)){
            return ResponseEntity.status( HttpStatus.OK ).build();
        }else {
            return ResponseEntity.status( 500 ).build();
        }
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<AuthorDto> findByLastName(@PathVariable String lastName) throws AuthorNotFoundException {
        AuthorDto authorDto=authorsService.findByLastName( lastName );
        return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK);
    }

}
