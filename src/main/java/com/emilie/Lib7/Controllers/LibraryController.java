
package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Exceptions.LoanAlreadyExistsException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/libraries")
public class LibraryController {

    private final LibraryService libraryService;



    @Autowired
    public LibraryController(LibraryService libraryService){

        this.libraryService = libraryService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDto> getById(@PathVariable(value="id") Long id) throws LibraryNotFoundException{
        LibraryDto libraryDto = libraryService.findById( id );
        return new ResponseEntity<LibraryDto>(libraryDto, HttpStatus.OK );
    }

    @GetMapping("/libraryList")
    public List<LibraryDto> findAll(){return this.libraryService.findAll();}

    @GetMapping("/{id}/copyCatalog")
    public Set<CopyDto> findCopiesByLibraryId(@PathVariable(value="id") Long id){
        return libraryService.findCopiesByLibraryId( id );
    }





    @PostMapping("/newLibrary")
    public ResponseEntity<String> save(@RequestBody LibraryDto libraryDto) throws LoanAlreadyExistsException {
        libraryService.save( libraryDto );
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @PutMapping("/updateLibrary")
    public ResponseEntity<LibraryDto> update(@RequestBody LibraryDto libraryDto) throws LibraryNotFoundException{
        LibraryDto libraryDto1 = libraryService.update(libraryDto);
        return new ResponseEntity<LibraryDto>(libraryDto1, HttpStatus.OK  );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id) throws LibraryNotFoundException{
        if (libraryService.deleteById( id )){
            return ResponseEntity.status( HttpStatus.OK ).build();
        }else {
            return ResponseEntity.status( 500 ).build();
        }
    }





}

