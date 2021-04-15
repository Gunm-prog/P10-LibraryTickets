
package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Library;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private  LibraryRepository libraryRepository;


    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository=libraryRepository;
    }


    @Override
    public List<LibraryDto> findAll() {
        libraryRepository.findAll();
        return null;
    }


    @Override
    public Library findById(Long id) {
        Optional<Library> optionalLibrary=libraryRepository.findById( id );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "Book not found" );
        }
        return optionalLibrary.get();
    }



 @Override
    public List<LibraryDto> findByAddress(AddressDto address) {
      return null;}

 //TODO méthodes conversions
}


