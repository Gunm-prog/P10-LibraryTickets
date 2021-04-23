package com.emilie.Lib7.Models.Entities;

import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="copy")
@EqualsAndHashCode(exclude={"library", "book"})
/*@JsonIdentityInfo( generator=ObjectIdGenerators.PropertyGenerator.class, property="id")*/
public class Copy implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="copy_id")
    private Long id;

    @Column(name="available", nullable=false)
    private boolean available;

    @JsonIgnoreProperties("copies")
    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @JsonIgnoreProperties("copies")
    @ManyToOne
    @JoinColumn(name="library_id", nullable=false)
    private Library library;



    /*private BookDto bookDto;
    private LibraryDto libraryDto;*/

}

