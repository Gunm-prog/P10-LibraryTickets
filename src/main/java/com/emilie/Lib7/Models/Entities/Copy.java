package com.emilie.Lib7.Models.Entities;

import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="copy")
public class Copy implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="copy_id")
    private Long id;

    @Column(name="available", nullable=false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="library_id", nullable=false)
    private Library library;

    private BookDto bookDto;
    private LibraryDto libraryDto;

}

