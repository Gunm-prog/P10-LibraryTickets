package com.emilie.Lib7.Models.Entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="library")
@Data
@NoArgsConstructor
public class Library implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="library_id")
    private Long libraryId;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="phoneNumber", length=10, nullable=false)
    private String phoneNumber;

    /*@ManyToOne
    @JoinColumn(name="adress_id")
    private Address address;*/

    /*@OneToMany(mappedBy="library")
    private Set<Copy> copies;
*/


}
