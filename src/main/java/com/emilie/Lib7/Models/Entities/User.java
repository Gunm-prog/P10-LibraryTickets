package com.emilie.Lib7.Models.Entities;

import com.emilie.Lib7.Models.Dtos.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
public class User implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long userId;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="fist_name", nullable=false)
    private String firstName;

    @Column(name="userName", nullable=false)
    private String userName;

    @Column(name="email", length=50, unique=true/*, nullable=false*/)
    private String email;

   /* @Column(name="cardNumber", length=50, unique=true, nullable=false)
    private String cardNumber;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @Column(name="registration_date", nullable=false)
    private DateTime registrationDate;


*/



}
