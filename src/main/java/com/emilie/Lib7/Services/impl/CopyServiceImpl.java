package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.CopyAlreadyExistException;
import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Models.Entities.Library;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.BookService;
import com.emilie.Lib7.Services.contract.CopyService;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;
    private final BookService bookServiceImpl;
    private final LibraryService libraryServiceImpl;


    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository, BookServiceImpl bookServiceImpl, LibraryServiceImpl libraryServiceImpl) {
        this.copyRepository=copyRepository;
        this.bookServiceImpl=bookServiceImpl;
        this.libraryServiceImpl=libraryServiceImpl;
    }

    @Override
    public List<CopyDto> findAll() {
        return null;
    }


    @Override
    public CopyDto findById(Long id) throws CopyNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "copy not found" );
        }
        Copy copy=optionalCopy.get();
        return copyToCopyDto( copy );

    }



    @Override
    public CopyDto save(CopyDto copyDto) throws CopyAlreadyExistException {
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        if (optionalCopy.isPresent()) {
            throw new CopyAlreadyExistException( "Copy already exists" );
        }
        Copy copy=copyDtoToCopy( copyDto );
        copy=copyRepository.save( copy );
        return copyDto;
    }


    @Override
    public CopyDto update(CopyDto copyDto) {
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        Copy copy=copyDtoToCopy( copyDto );
        copy=copyRepository.save( copy );
        return copyToCopyDto( copy );
    }


    @Override
    public boolean deleteById(Long id) throws CopyNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "Copy not found" );
        }
        try {
            copyRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;

    }


    private CopyDto copyToCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );

        BookDto bookDto = new BookDto();
        bookDto.setBookId( copy.getBook().getBookId() );
        bookDto.setTitle( copy.getBook().getTitle() );
        bookDto.setIsbn( copy.getBook().getIsbn() );
        bookDto.setSummary( copy.getBook().getSummary() );
        bookDto.setAuthor( copy.getBook().getAuthor() );
        copyDto.setBookDto( bookDto );

        LibraryDto libraryDto = new LibraryDto();
        //TODO faire libraryDto
        copyDto.setLibraryDto( libraryDto );

        return copyDto;
    }


    private Copy copyDtoToCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );

        Book book = new Book();
        book.setBookId(copyDto.getBookDto().getBookId());
        book.setTitle(copyDto.getBookDto().getTitle());
        book.setIsbn( copyDto.getBookDto().getIsbn() );
        book.setSummary( copyDto.getBookDto().getSummary() );
        book.setAuthor( copyDto.getBookDto().getAuthor() );
        copy.setBook( book );

        Library library = new Library();
        //TODO faire Library
        copy.setLibrary( library );

        return copy;

    }



}