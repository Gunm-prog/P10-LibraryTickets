package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.AddressNotFoundException;
import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.Address;
import com.emilie.Lib7.Models.Entities.Loan;
import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }




    @Override
    public UserDto findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( id );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "User not found" );
        }
        User user=optionalUser.get();
        /*UserDto userDto1= new UserDto();*/
        /*userDto.setLastName( user.getLastName() ); //récupération info user pour les mettre dans userDto/transvasage*/
        return userToUserDto( user );
    }

    @Override
    public UserDto save(UserDto userDto) throws UserAlreadyExistException {
        Optional<User> optionalUser=userRepository.findByEmail( userDto.getEmail() );
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException( "Email already exists" );
        }
        if(userDto.getAddressDto() == null){
            throw new AddressNotFoundException( "Address not found" );
        }

        User user=userDtoToUser( userDto );
        user=userRepository.save( user );

        return userToUserDto( user );
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> optionalUser=userRepository.findById( userDto.getUserId() );
        User user=userDtoToUser( userDto );
        user=userRepository.save( user );
        return userToUserDto( user );
    }


   /* @Override
    public*/


    @Override
    public boolean deleteById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( id );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "user not found" );
        }
        try {
            userRepository.deleteById( id );
        } catch (Exception e) {
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

//TODO conversion methods
    @Override
    public UserDto findByLastName(String lastName) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByLastName( lastName );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "User not found" );
        }
        User user=optionalUser.get();
        UserDto userDto=new UserDto();
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        userDto.setUserName( user.getUserName() );
        userDto.setFirstName( user.getFirstName() );

        return userDto;
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail( email );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "User not found" );
        }
        User user=optionalUser.get();
        UserDto userDto=this.userToUserDto( user );
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

    // Les méthodes ci-dessous sont en private, parce qu'elles ne sont pas destinées à être utilisées en dehors de la class UserServiceImpl.
    // mais uniquement par elle-mêmes. Pour les utiliser, il faut utiliser le mot-clef this.NomDeMéthode.
    // Le this, fait référence à l'instance qui est exécutée. donc ici UserServiceImpl
    // de sorte que this.userToDto( unUser ) placé dans une méthode de UserServiceImpl, appelera la méthode ci-dessous
    private UserDto userToUserDto(User user) {
        UserDto userDto=new UserDto();
        userDto.setUserId( user.getUserId() );
        userDto.setUserName( user.getUserName() );
        userDto.setEmail( user.getEmail() );
        userDto.setLastName( user.getLastName() );
        userDto.setFirstName( user.getFirstName() );

        Set<LoanDto> loanDtos = new HashSet<>();
        if (user.getLoans() != null){
            for (Loan loan : user.getLoans()){
                LoanDto loanDto = new LoanDto();
                loanDto.setId(loan.getId());
                loanDto.setLoanStartDate(loan.getLoanStartDate());
                loanDto.setLoanEndDate( loan.getLoanEndDate() );
                loanDto.setExtended( loan.isExtended() );
                loanDtos.add(loanDto);
            }
            userDto.setLoanDtos( loanDtos );

        }

        AddressDto addressDto = new AddressDto();
        addressDto.setNumber( user.getAddress().getNumber() );
        addressDto.setStreet( user.getAddress().getStreet() );
        addressDto.setZipCode( user.getAddress().getZipCode() );
        addressDto.setCity( user.getAddress().getCity() );
        userDto.setAddressDto( addressDto );


        return userDto;
    }

    private User userDtoToUser(UserDto userDto) {
        User user=new User();
        user.setUserId( userDto.getUserId() );
        user.setUserName( userDto.getUserName() );
        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        Set<Loan> loans = new HashSet<>();
        if (userDto.getLoanDtos() != null){
            for(LoanDto loanDto : userDto.getLoanDtos()){
                Loan loan = new Loan();
                loan.setId( loanDto.getId() );
                loan.setLoanStartDate( loanDto.getLoanStartDate() );
                loan.setLoanEndDate( loanDto.getLoanEndDate() );
                loan.setExtended( loanDto.isExtended() );
                loans.add(loan);
            }
            user.setLoans( loans );
        }

        Address address = new Address();
        address.setNumber( userDto.getAddressDto().getNumber() );
        address.setStreet( userDto.getAddressDto().getStreet() );
        address.setZipCode( userDto.getAddressDto().getZipCode() );
        address.setCity( userDto.getAddressDto().getCity() );
        user.setAddress( address );

        return user;
    }

}