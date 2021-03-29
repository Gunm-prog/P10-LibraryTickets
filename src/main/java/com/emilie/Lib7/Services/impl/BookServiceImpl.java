package com.emilie.Lib7.Services.impl;



import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import com.emilie.Lib7.Services.contract.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookService bookService;

    private List<BookDto> bookDto;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository=bookRepository;

    }


   /* @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        bookRepository.findAll();
       *//* return bookDto;
    }*/

    @Override
    public List<BookDto> findAll() {
        List<Book> books=bookRepository.findAll();
        List<BookDto> bookDtos=new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto=new BookDto();
            bookDto.setTitle( book.getTitle() );
        }
        return bookDto;
    }

    @Override
    public BookDto findById(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( id );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "Book not found" );
        }
        Book book=optionalBook.get();
        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        return bookDto;
    }

    @Override
    public BookDto findByTitle(String title) {
        Optional<Book> optionalBook=bookRepository.findByTitle( title );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "books not found" );
        }
        Book book=optionalBook.get();
        BookDto bookDto=new BookDto();
        bookDto.setTitle( book.getTitle() );
        return bookDto;
    }

    @Override
    public BookDto findByAuthor(Author author) {
        Optional<Book> optionalBook=bookRepository.findByAuthor( author );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book not found" );
        }
        Book book=optionalBook.get();
        BookDto bookDto=new BookDto();
        bookDto.setAuthor( author );
        return bookDto;

        /*return this.bookRepository.findByAuthor(author);*/
    }


}


