package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        UserDto userDto = new UserDto();
        userDto.setLastName( user.getLastName() ); //récupération info user pour les mettre dans userDto/transvasage
        return userDto;
    }

   /* @Override
    public UserDto save(User user) throws UserAlreadyExistException {
        Optional<User> registeredUser = userRepository.findByEmail(user.getEmail());
        if (registeredUser.isEmpty()){
            throw new UserAlreadyExistException( "User already exists with this email" );
        }
        User savedUser = new User();
        UserDto userDto = new UserDto();
        userDto.setEmail(savedUser.getEmail());
        return userDto;*/

        /*savedUser.setRegistrationDate( DateTime.now());
        Optional<User> save = userRepository.save( savedUser );
        return savedUser;*/
   /* }*/

    @Override
    public UserDto findByLastName(String lastName) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findByLastName( lastName );
        if (!optionalUser.isPresent()){
           throw new UserNotFoundException( "User not found" );
        }
        User user = optionalUser.get();
        UserDto userDto = new UserDto();
        userDto.setLastName( user.getLastName() );
        return userDto;
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail( email );
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException( "User not found" );
        }
        User user =optionalUser.get();
        UserDto userDto = new UserDto();
        userDto.setEmail( user.getEmail() );
        return userDto;
    }

   /* @Override
    public UserDto deleteById(Long id) {
        userRepository.deleteById( id );
        return null;
    }

    @Override
    public UserDto findByCardNumber(String cardNumber) {
        return null;
    }
*/

}
