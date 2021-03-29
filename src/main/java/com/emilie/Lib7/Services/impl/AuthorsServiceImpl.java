
package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Repositories.AuthorsRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorsServiceImpl implements AuthorsService {

   
    private AuthorsRepository authorsRepository;
    private AuthorsService authorsService;
    private List<AuthorDto> authorDto;


    @Autowired
    public AuthorsServiceImpl (AuthorsRepository authorsRepository){
        this.authorsRepository = authorsRepository;
    }

    @Override
    public List<AuthorDto> findAll(){
    List<Author> authors = authorsRepository.findAll();
    List<AuthorDto> authorDtos = new ArrayList<>();
    for (Author author : authors) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setLastName( author.getLastName() );
    }return authorDto;
    }

    @Override
    public AuthorDto findById(Long id) throws AuthorNotFoundException {
        Optional<Author> optionalAuthor = authorsRepository.findById( id );
        if (!optionalAuthor.isPresent()){
           throw new AuthorNotFoundException("Author not found");
        }
        Author author = optionalAuthor.get();
        AuthorDto authorDto = new AuthorDto();
        authorDto.setLastName(author.getLastName());
        return authorDto;
    }

    @Override
    public AuthorDto findByFirstName(String firstName){
    Optional<Author> optionalAuthor = authorsRepository.findByFirstName( firstName );
    if (!optionalAuthor.isPresent()){
        throw new AuthorNotFoundException("author not found" );
    }
    Author author = optionalAuthor.get();
    AuthorDto authorDto = new AuthorDto();
    authorDto.setFirstName( author.getFirstName() );
    return authorDto;
    }

    @Override
    public AuthorDto findByLastName(String lastName){
    Optional<Author> optionalAuthor = authorsRepository.findByLastName( lastName );
    if (!optionalAuthor.isPresent()){
        throw new AuthorNotFoundException( "author not found" );
    }
    Author author = optionalAuthor.get();
    AuthorDto authorDto = new AuthorDto();
    author.setLastName( author.getLastName() );
    return authorDto;
    }

}
