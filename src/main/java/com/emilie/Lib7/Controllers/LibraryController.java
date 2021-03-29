/*
package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Address;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/lookingFor")
public class LibraryController {

    private final LibraryService libraryService;
   */
/* private final BookService bookService;
    private AuthorsService authorsService;*//*

    private LibraryDto libraryDto;
  */
/*  private AddressDto addressDto;
    private BookDto bookDto;*//*


    @Autowired
    public LibraryController(LibraryService libraryService
                             */
/*AuthorsService authorsService,
                             BookService bookService*//*
) {
        this.libraryService=libraryService;
        */
/*this.bookService=bookService;
        this.authorsService=authorsService*//*
;
    }

    */
/*@GetMapping("/library-list")
    public List<LibraryDto> getAllLibraries(){
        return this.libraryService.findAll();
    }*//*


  */
/*  @GetMapping("/library-address")
    public List<LibraryDto> getByAddress(Address address){
        libraryDto.setAddressDto(addressDto);
        return this.libraryService.findByAddress( addressDto );
    }*//*



}
*/
