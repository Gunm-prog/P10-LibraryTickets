package com.emilie.Lib7.Models.Entities;


import com.emilie.Lib7.Models.Dtos.AuthorDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="author")
public class Author implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="author_id")
    private Long authorId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @OneToMany(mappedBy="author")
    private Set<Book> books;

    public void setId(Long id) {
    }


}
