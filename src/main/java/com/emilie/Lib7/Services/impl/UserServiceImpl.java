package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.AddressNotFoundException;
import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.*;
import com.emilie.Lib7.Models.Entities.*;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public UserDto getLoggedUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = findByEmail( email );
        return userDto;
    }


    @Override
    public UserDto findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( id );
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException( "User not found" );
        }
        User user=optionalUser.get();
        /*UserDto userDto1= new UserDto();*/
        /*userDto.setLastName( user.getLastName() ); //récupération info user pour les mettre dans userDto/transvasage*/
        return userToUserDto( user );
    }

    @Override
    public UserDto save(UserDto userDto) throws UserAlreadyExistException {
        isNewUserValid( userDto );

        System.out.println(userDto);
        User user=userDtoToUser( userDto );
        user=userRepository.save( user );

        return userToUserDto( user );
    }

    @Override
    public void isNewUserValid(UserDto userDto){
        Optional<User> optionalUser=userRepository.findByEmail( userDto.getEmail() );
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException( "Email already exists" );
        }
        if(userDto.getAddressDto() == null){
            throw new AddressNotFoundException( "Address not found" );
        }
    }



    @Override
    public UserDto update(UserDto userDto)  throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( userDto.getUserId() );
        if (userDto.getAddressDto() == null){
            throw new AddressNotFoundException( "Address not found" );
        }
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }

        User user=optionalUser.get();
        System.out.println("ligne 91 : " + user);
        //TODO username refresh token
        /*user.setUsername( userDto.getUsername() );*/
        //TODO Email refresh token
        /*user.setEmail( userDto.getEmail() );*/
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setAddress(makeAddress(userDto.getAddressDto() ));

        user=userRepository.save( user );
        return userToUserDto( user );
    }



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
        userDto.setUsername( user.getUsername() );
        userDto.setFirstName( user.getFirstName() );

        return userDto;
    }

   /* @Override
    public UserDto findByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername( username );
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException( "User not found" );
        }
        User user = optionalUser.get();
        return this.userToUserDto( user );
    }*/




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
        userDto.setUserId( user.getId() );
        userDto.setPassword(user.getPassword());
        userDto.setRoles( user.getRoles() );
        userDto.setActive( user.isActive() );
        userDto.setEmail( user.getEmail() );
        userDto.setLastName( user.getLastName() );
        userDto.setFirstName( user.getFirstName() );

        Set<LoanDto> loanDtos = new HashSet<>();
        if (user.getLoans() != null){
            for (Loan loan : user.getLoans()){
                LoanDto loanDto = makeLoanDto( loan );
                loanDtos.add(loanDto);
            }
            userDto.setLoanDtos( loanDtos );

        }

        AddressDto addressDto =  makeAddressDto(user.getAddress());

        userDto.setAddressDto( addressDto );


        return userDto;
    }





    private User userDtoToUser(UserDto userDto) {
        User user=new User();
        user.setId( userDto.getUserId() );
        user.setPassword( userDto.getPassword() );
        user.setEmail( userDto.getEmail() );
        user.setRoles( userDto.getRoles() );
        user.setActive( userDto.isActive() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        Set<Loan> loans = new HashSet<>();
        if (userDto.getLoanDtos() != null){
            for(LoanDto loanDto : userDto.getLoanDtos()){
                Loan loan = makeLoan( loanDto );
                loans.add(loan);
            }
            user.setLoans( loans );
        }

        Address address =  makeAddress(userDto.getAddressDto());
        user.setAddress( address );

        return user;
    }


    private AddressDto makeAddressDto(Address address){

        AddressDto addressDto = new AddressDto();
        addressDto.setNumber( address.getNumber() );
        addressDto.setStreet( address.getStreet() );
        addressDto.setZipCode( address.getZipCode() );
        addressDto.setCity( address.getCity() );

        return addressDto;
    }

    private Address makeAddress(AddressDto addressDto){

        Address address = new Address();
        address.setNumber( addressDto.getNumber() );
        address.setStreet( addressDto.getStreet() );
        address.setZipCode( addressDto.getZipCode() );
        address.setCity( addressDto.getCity() );

        return address;
    }

    private LoanDto makeLoanDto(Loan loan){
        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setLoanStartDate(loan.getLoanStartDate());
        loanDto.setLoanEndDate( loan.getLoanEndDate() );
        loanDto.setExtended( loan.isExtended() );
        loanDto.setCopyDto( makeCopyDto( loan.getCopy() ) );
        return loanDto;
    }

    private Loan makeLoan(LoanDto loanDto){

        Loan loan = new Loan();
        loan.setId( loanDto.getId() );
        loan.setLoanStartDate( loanDto.getLoanStartDate() );
        loan.setLoanEndDate( loanDto.getLoanEndDate() );
        loan.setExtended( loanDto.isExtended() );
        loan.setCopy( makeCopy( loanDto.getCopyDto() ) );
        return loan;
    }

    private CopyDto makeCopyDto(Copy copy){
        CopyDto copyDto = new CopyDto();
        copyDto.setId(copy.getId());
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setBookDto( makeBookDto( copy.getBook() ) );
        copyDto.setLibraryDto( makeLibraryDto( copy.getLibrary() ) );
        return copyDto;
    }

    private Copy makeCopy(CopyDto copyDto){
        Copy copy = new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );
        copy.setBook( makeBook( copyDto.getBookDto() ) );
        copy.setLibrary( makeLibrary( copyDto.getLibraryDto() ) );
        return copy;
    }

    private BookDto makeBookDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setAuthorDto( makeAuthorDto( book.getAuthor() ) );
        return bookDto;
    }

    private Book makeBook(BookDto bookDto){
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setAuthor(makeAuthor(bookDto.getAuthorDto()));
        return book;
    }

    private LibraryDto makeLibraryDto(Library library){
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setLibraryId(library.getLibraryId() );
        libraryDto.setName( library.getName() );
        return libraryDto;
    }

    private Library makeLibrary(LibraryDto libraryDto){
        Library library = new Library();
        library.setLibraryId( libraryDto.getLibraryId() );
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );
        library.setAddress(makeAddress(libraryDto.getAddressDto() ) );
        return library;
    }

    private AuthorDto makeAuthorDto(Author author){
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorId( author.getAuthorId() );
        authorDto.setFirstName( author.getFirstName() );
        authorDto.setLastName( author.getLastName() );
        return authorDto;
    }

    private Author makeAuthor(AuthorDto authorDto){
        Author author = new Author();
        author.setAuthorId( authorDto.getAuthorId());
        author.setFirstName( authorDto.getFirstName() );
        author.setLastName( authorDto.getLastName() );
        return author;
    }


}