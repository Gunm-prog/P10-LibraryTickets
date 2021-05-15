package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Exceptions.BookAlreadyExistException;
import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Repositories.AuthorsRepository;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import com.emilie.Lib7.Services.contract.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorsRepository authorsRepository;
    private final CopyRepository copyRepository;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorsRepository authorsRepository,
                           CopyRepository copyRepository) {
        this.bookRepository=bookRepository;
        this.authorsRepository=authorsRepository;
        this.copyRepository=copyRepository;
    }


    @Override
    public List<BookDto> findAll() {
        List<Book> books=bookRepository.findAll();
        List<BookDto> bookDtos=new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto =  bookToBookDto( book );
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    @Override
    public BookDto findById(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( id );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "Book not found" );
        }
        Book book=optionalBook.get();
        return bookToBookDto( book );
       /* Book book=optionalBook.get();
        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        return bookDto;*/
    }

    @Override
    public BookDto save(BookDto bookDto) throws BookAlreadyExistException {
        Optional<Book> optionalBook=bookRepository.findByTitle( bookDto.getTitle() );
        if (optionalBook.isPresent()) {
            throw new BookAlreadyExistException( "book already exists" );
        }
        Optional<Author> optionalAuthor=authorsRepository.findById( bookDto.getAuthorDto().getAuthorId() );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "author not found" );
        }
        Book book=bookDtoToBook( bookDto );
        book.setAuthor( optionalAuthor.get() );
        book=bookRepository.save( book );
        return bookToBookDto( book );
    }


    @Override
    public BookDto update(BookDto bookDto) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( bookDto.getBookId() );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book not found" );
        }

        Book book=optionalBook.get();
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setSummary( bookDto.getSummary() );
        book=bookRepository.save( book );
        return bookToBookDto( book );
    }


    @Override
    public boolean deleteById(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( id );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book not found" );
        }
        try {
            bookRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public BookDto findByTitle(String title) throws UnsupportedEncodingException {
        /*title=URLDecoder.decode( title, StandardCharsets.UTF_8.toString() );*/
        Optional<Book> optionalBook=bookRepository.findByTitle( title );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "books not found" );
        }
        Book book=optionalBook.get();
        BookDto bookDto=this.bookToBookDto( book );
        return bookDto;
    }


    @Override
    public BookDto findByAuthor(Author author) {
        Optional<Book> optionalBook=bookRepository.findByAuthor( author );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book not found" );
        }
        Book book=optionalBook.get();
        return this.bookToBookDto( book );
    }


    private BookDto bookToBookDto(Book book) {
        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setSummary( book.getSummary() );

        Set<CopyDto> copyDtos=new HashSet<>();
        if (book.getCopies() != null) {
            for (Copy copy : book.getCopies()) {
                CopyDto copyDto=new CopyDto();
                copyDto.setId( copy.getId() );
                copyDto.setAvailable( copy.isAvailable() );
                copyDtos.add( copyDto );
            }

            bookDto.setCopyDtos( copyDtos );


        }
        AuthorDto authorDto=new AuthorDto();
        authorDto.setAuthorId( book.getAuthor().getAuthorId() );
        authorDto.setFirstName( book.getAuthor().getFirstName() );
        authorDto.setLastName( book.getAuthor().getLastName() );
        bookDto.setAuthorDto( authorDto );

        return bookDto;
    }

    private Book bookDtoToBook(BookDto bookDto) {
        Book book=new Book();
        book.setBookId( bookDto.getBookId() );
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setSummary( bookDto.getSummary() );

        Set<Copy> copies=new HashSet<>();
        if (bookDto.getCopyDtos() != null) {
            for (CopyDto copyDto : bookDto.getCopyDtos()) {
                Copy copy=new Copy();
                copy.setId( copyDto.getId() );
                copy.setAvailable( copyDto.isAvailable() );
                copies.add( copy );
            }
            book.setCopies( copies );

        }
        Author author=new Author();
        author.setAuthorId( bookDto.getAuthorDto().getAuthorId() );
        author.setFirstName( bookDto.getAuthorDto().getFirstName() );
        author.setLastName( bookDto.getAuthorDto().getLastName() );
        book.setAuthor( author );

        return book;

    }


}