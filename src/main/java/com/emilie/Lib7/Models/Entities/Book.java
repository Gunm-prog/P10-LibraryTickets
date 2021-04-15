package com.emilie.Lib7.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="book")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude="author")
/*@JsonIdentityInfo( generator=ObjectIdGenerators.PropertyGenerator.class, property="id")*/
public class Book implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long bookId;

    @Column(name="title", length=50, nullable=false)
    private String title;

    @Column(name="isbn", length=30, nullable=false)
    private String isbn;

    @Column(name="summary", length=500)
    private String summary;


    @ManyToOne
    @JoinColumn(name="author_id",referencedColumnName="author_id")
    @JsonIgnoreProperties("books")
    private Author author;

    /*@OneToMany(mappedBy="book")
    private Set<Copy> copies;*/

    /*@ManyToOne
    @JoinColumn(name="cover_id")
    private Cover cover;*/

    /*@Enumerated(EnumType.STRING)
    @Column(name="category", length=20, nullable false)
    private Category category;
     */

    /*@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="category_id", referencedColumnName="id")
    private Category category;*/











}
