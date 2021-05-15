
package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.LibraryAlreadyExistException;
import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Models.Entities.Library;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;


    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository,
                              BookRepository bookRepository,
                              CopyRepository copyRepository) {

        this.libraryRepository=libraryRepository;
        this.bookRepository=bookRepository;
        this.copyRepository=copyRepository;
    }


    @Override
    public List<LibraryDto> findAll() {
        List<Library> libraries=libraryRepository.findAll();
        List<LibraryDto> libraryDtos=new ArrayList<>();
        for (Library library : libraries) {
            LibraryDto libraryDto= libraryToLibraryDto(library);
            libraryDtos.add(libraryDto);
        }
        return libraryDtos;
    }


    @Override
    public LibraryDto findById(Long id) throws LibraryNotFoundException {
        Optional<Library> optionalLibrary=libraryRepository.findById( id );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "Library not found" );
        }
        Library library=optionalLibrary.get();
        return libraryToLibraryDto( library );
    }

    @Override
    public LibraryDto save(LibraryDto libraryDto) throws LibraryAlreadyExistException {
        Optional<Library> optionalLibrary=libraryRepository.findByName( libraryDto.getName() );
        if (optionalLibrary.isPresent()) {
            throw new LibraryAlreadyExistException( "library already exists" );
        }
        Library library=libraryDtoToLibrary( libraryDto );
        library=libraryRepository.save( library );
        return libraryToLibraryDto( library );
    }

    @Override
    public LibraryDto update(LibraryDto libraryDto) throws LibraryNotFoundException {
        Optional<Library> optionalLibrary=libraryRepository.findById( libraryDto.getLibraryId() );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library not found" );
        }
        Library library=optionalLibrary.get();
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );
        library=libraryRepository.save( library );
        return libraryToLibraryDto( library );
    }

    @Override
    public boolean deleteById(Long id) throws LibraryNotFoundException {
        Optional<Library> optionalLibrary=libraryRepository.findById( id );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library not found" );
        }
        try {
            libraryRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public List<LibraryDto> findByAddress(AddressDto address) {
        return null;
    }


    private LibraryDto libraryToLibraryDto(Library library) {
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setLibraryId( library.getLibraryId() );
        libraryDto.setName( library.getName() );
        libraryDto.setPhoneNumber( library.getPhoneNumber() );


        Set<CopyDto> copyDtos=new HashSet<>();
        if (library.getCopies() != null) {
            for (Copy copy : library.getCopies()) {
                CopyDto copyDto=new CopyDto();
                copyDto.setId( copy.getId() );
                copyDto.setAvailable( copy.isAvailable() );
                copyDtos.add( copyDto );
            }
            libraryDto.setCopyDtos( copyDtos );
        }
        return libraryDto;

    }

    private Library libraryDtoToLibrary(LibraryDto libraryDto) {
        Library library=new Library();
        library.setLibraryId( libraryDto.getLibraryId() );
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );

        Set<Copy> copies=new HashSet<>();
        if (libraryDto.getCopyDtos() != null) {
            for (CopyDto copyDto : libraryDto.getCopyDtos()) {
                Copy copy=new Copy();
                copy.setId( copyDto.getId() );
                copy.setAvailable( copyDto.isAvailable() );
                copies.add( copy );
            }
            library.setCopies( copies );
        }
        return library;

    }

}
