package com.emilie.Lib7.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//@Entity
@Embeddable
@Table(name="address")
@Data
@NoArgsConstructor
public class Address implements Serializable{

    private static final long serialVersionUID = 1L;

    /*@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name ="address_id")
    private Long addressId;*/

    @Column(name="number", nullable=false)
    private int number;

    @Column(name="street", length=50, nullable=false)
    private String street;

    @Column(name="zip_code", length=5, nullable=false)
    private String zipCode;

    @Column(name="city", nullable=false)
    private String city;




}
