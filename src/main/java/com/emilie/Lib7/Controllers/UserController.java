package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.User;
import io.swagger.annotations.ApiOperation;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private  UserService userService;


    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "Retrieve a user account thanks to its Id, if the user is registered in database")
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable(value = "id") Long id) throws UserNotFoundException {
        return this.userService.findById(id);
    }

    @ApiOperation( value="Retrieve user list from the database" )
    @GetMapping("/userList")
    public List<UserDto> findAll(){
        return  this.userService.findAll();
    }


    @PostMapping("/createUserAccount")
    public ResponseEntity<String> save( @RequestBody UserDto userDto)throws UserAlreadyExistException {
        userService.save(userDto);
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
       UserDto userDto1= userService.update(userDto);
        return new ResponseEntity<UserDto>( userDto1, HttpStatus.OK );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id) throws UserNotFoundException {
        if (userService.deleteById( id )) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(500).build();
        }

    }



    @ApiOperation( value="Retrieve a user account thanks to its last name, if the user is registered in database")
    @GetMapping("/last-name/{last-name}")
    public UserDto getByLastName(@PathVariable(value="last-name") String lastName) throws UserNotFoundException {
        /*userDto.setLastName( lastName );*/
        return this.userService.findByLastName(lastName);
    }

    @ApiOperation( value="Retrieve a user account thanks to its email, if the user is registered in database")
    @GetMapping("/email/{email}")
    public UserDto getByEmail(@PathVariable String email) throws UserNotFoundException {
        return this.userService.findByEmail(email);
    }




    /*@ApiOperation( value= "Retrieve a user account from library card number, if user is registered in database" )
    @GetMapping ("/card-number")
    public UserDto getBycardNumber(@PathVariable(value="card_number") String cardNumber){
        return this.userService.findByCardNumber(cardNumber);
    }*/


}
