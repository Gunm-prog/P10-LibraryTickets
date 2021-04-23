package com.emilie.Lib7.Models.Dtos;


import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class AuthorDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long authorId;
    private String firstName;
    private String lastName;
    private Set<BookDto> bookDtos;





}