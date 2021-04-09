package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    // Les méthodes ci-dessous sont en private, parce qu'elles ne sont pas destiné à être utilisé en dehors de la class UserServiceImpl.
    // mais uniquement par elle même. Pour les utilisés, il faut utilisé le mot clef this.NomDeMéthode.
    // Le this, fait référence l'instance qui est exécuté. donc ici UserServiceImpl
    // de sorte que this.userToDto( unUser ) placé dans une méthode de UserServiceImpl, appelera la méthode ci-dessous
    private UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId( user.getUserId() );
        userDto.setUserName( user.getUserName() );
        userDto.setEmail( user.getEmail() );
        userDto.setLastName( user.getLastName() );
        userDto.setFirstName( user.getFirstName() );
        return userDto;
    }

    private User userDtoToUser(UserDto userDto){
        User user = new User();
        user.setUserId( userDto.getId() );
        user.setUserName( userDto.getUserName() );
        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        return user;
    }

    @Override
    public UserDto findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        /*UserDto userDto1= new UserDto();*/
        /*userDto.setLastName( user.getLastName() ); //récupération info user pour les mettre dans userDto/transvasage*/
        return userToUserDto( user );
    }

    @Override
    public UserDto save (UserDto userDto) throws UserAlreadyExistException {
        Optional<User> optionalUser=userRepository.findByEmail( userDto.getEmail() );
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException( "Email already exists" );
        }
        User user = userDtoToUser( userDto );
        userRepository.save( user );

        return userToUserDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> optionalUser=userRepository.findById( userDto.getId() );
        User user = userDtoToUser( userDto );
        userRepository.save(user);
        return userToUserDto( user );
    }


    @Override
    public  boolean deleteById(Long id) throws UserNotFoundException{
        Optional<User> optionalUser  = userRepository.findById(id );
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException( "user not found" );
        }
         try{
             userRepository.deleteById( id );
         }catch (Exception e){
             return false;
         }

        return true;
    }



   /* @Override
    public UserDto save(User user) throws UserAlreadyExistException {
        Optional<User> registeredUser = userRepository.findByEmail(user.getEmail());
        if (registeredUser.isPresent()){
            throw new UserAlreadyExistException( "User already exists with this email" );
        }
        User savedUser = new User();
        UserDto userDto = new UserDto();
        userDto.setEmail(savedUser.getEmail());
        return userDto;
*/
        /*savedUser.setRegistrationDate( DateTime.now());
        Optional<User> save = userRepository.save( savedUser );
        return savedUser;
        }*/






    @Override
    public UserDto findByLastName(String lastName) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findByLastName( lastName );
        if (!optionalUser.isPresent()){
           throw new UserNotFoundException( "User not found" );
        }
        User user = optionalUser.get();
        UserDto userDto = new UserDto();
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        userDto.setUserName( user.getUserName() );
        userDto.setFirstName( user.getFirstName() );
        return userDto;
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail( email );
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException( "User not found" );
        }
        User user =optionalUser.get();
        UserDto userDto = this.userToUserDto( user );
        //Au lieu de placé le retour de la méthode userToUserDto dans une variable userDto, on peut aussi directement le retourner
        //a voir selon préférence.

        //le this permet de pointer vers la méthode demandé dans la class utilisé, (ici on est dans UserServiceImpl)
        //donc this.userToUserDto appelera la méthode userToUserDto de la class UserServiceImpl
        //this. est un mot clef qui permet à une class de faire référence à elle même.

        //Le code ci dessous n'est plus nécessaire, puisque c'est ce que va faire la nouvelle méthode userToUserDto
       /* UserDto userDto = new UserDto();
        userDto.setEmail( user.getEmail() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setUsername( user.getUserName() );
        userDto.setLastName( user.getLastName() );
        userDto.setId( user.getUserId() );*/
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

}
