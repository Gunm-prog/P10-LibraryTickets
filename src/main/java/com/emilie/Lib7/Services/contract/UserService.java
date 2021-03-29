package com.emilie.Lib7.Services.contract;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.User;

public interface UserService {

    UserDto findById(Long id) throws UserNotFoundException;

   /* UserDto save(User user) throws UserAlreadyExistException;*/

    UserDto findByLastName (String lastName) throws UserNotFoundException;

    UserDto findByEmail (String email) throws UserNotFoundException;

 /*   UserDto deleteById(Long id);


    UserDto findByCardNumber(String cardNumber);*/
}
