package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String cardNumber;
    private DateTime registrationDate;
    private AddressDto addressDto;

}

