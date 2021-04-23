package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Exceptions.CopyAlreadyExistException;
import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Models.Entities.Library;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import com.emilie.Lib7.Services.contract.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;


    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository,
                           BookRepository bookRepository,
                           LibraryRepository libraryRepository) {

        this.copyRepository=copyRepository;
        this.bookRepository=bookRepository;
        this.libraryRepository=libraryRepository;
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
    public CopyDto save(CopyDto copyDto) throws BookNotFoundException, LibraryNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById( copyDto.getBookDto().getBookId() );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book not found" );
        }
        Optional<Library> optionalLibrary = libraryRepository.findById( copyDto.getLibraryDto().getLibraryId() );
        if (!optionalLibrary.isPresent()){
            throw new LibraryNotFoundException( "library not found" );
        }
        Copy copy=copyDtoToCopy( copyDto );
        copy.setBook(optionalBook.get());
        copy.setLibrary( optionalLibrary.get() );
        copy=copyRepository.save( copy );
        return copyToCopyDto( copy );
    }


    @Override
    public CopyDto update(CopyDto copyDto) throws CopyNotFoundException, BookNotFoundException, LibraryNotFoundException{
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        if (!optionalCopy.isPresent()){
            throw new CopyNotFoundException( "copy not found" );
        }
        Optional<Library> optionalLibrary = libraryRepository.findById( copyDto.getLibraryDto().getLibraryId() );
        if (!optionalLibrary.isPresent()){
            throw new LibraryNotFoundException( "library not found" );
        }

        Copy copy=optionalCopy.get();
        copy.setAvailable( copyDto.isAvailable() );

        copy.setBook( extractBookFromCopyDto(copyDto) );
        copy.setLibrary( optionalLibrary.get() );

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

        Book book = copy.getBook();
        BookDto bookDto = new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setSummary( book.getSummary() );
        copyDto.setBookDto( bookDto );

        LibraryDto libraryDto = new LibraryDto();
        //TODO faire libraryDto
        /*copyDto.setLibraryDto( libraryDto );
*/
        return copyDto;
    }

    private Copy copyDtoToCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );

        Book book = new Book();
        book.setBookId(copyDto.getBookDto().getBookId() );
        book.setTitle(copyDto.getBookDto().getTitle() );
        book.setIsbn(copyDto.getBookDto().getIsbn());
        book.setSummary(copyDto.getBookDto().getSummary());
        copy.setBook( book );

        Library library = new Library();
        //TODO faire Library
        copy.setLibrary( library );

        return copy;

    }

    private Book extractBookFromCopyDto(CopyDto copyDto){
        Book book = new Book();
        book.setBookId(copyDto.getBookDto().getBookId() );
        book.setTitle(copyDto.getBookDto().getTitle() );
        book.setIsbn(copyDto.getBookDto().getIsbn());
        book.setSummary(copyDto.getBookDto().getSummary());

        return book;
    }



}