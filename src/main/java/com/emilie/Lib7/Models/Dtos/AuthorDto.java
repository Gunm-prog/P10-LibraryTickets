package com.emilie.Lib7.Models.Dtos;


import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthorDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long authorId;
    private String firstName;
    private String lastName;
    private List<BookDto> books;



}