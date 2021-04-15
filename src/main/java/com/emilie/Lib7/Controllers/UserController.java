package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
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

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService=userService;
    }

    @ApiOperation(value="Retrieve a user account thanks to its Id, if the user is registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value="id") Long id) throws UserNotFoundException {
       UserDto userDto = userService.findById( id );
       return new ResponseEntity<UserDto>(userDto, HttpStatus.OK  );
    }

    @ApiOperation(value="Retrieve user list from the database")
    @GetMapping("/userList")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userDtos = userService.findAll();
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK  );
    }

    @ApiOperation( value="Create an account saved in database containing the informations given by user" )
    @PostMapping("/createUserAccount")
    public ResponseEntity<String> save(@RequestBody UserDto userDto) throws UserAlreadyExistException {
        userService.save( userDto );
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @ApiOperation( value="update user saving modifications in database" )
    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        UserDto userDto1=userService.update( userDto );
        return new ResponseEntity<UserDto>( userDto1, HttpStatus.OK );
    }

    @ApiOperation( value="delete user from database by id" )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id) throws UserNotFoundException {
        if (userService.deleteById( id )) {
            return ResponseEntity.status( HttpStatus.OK ).build();
        } else {
            return ResponseEntity.status( 500 ).build();
        }

    }

    //TODO ResponseEntity
    @ApiOperation(value="Retrieve a user account thanks to its last name, if the user is registered in database")
    @GetMapping("/last-name/{last-name}")
    public UserDto getByLastName(@PathVariable(value="last-name") String lastName) throws UserNotFoundException {
        /*userDto.setLastName( lastName );*/
        return this.userService.findByLastName( lastName );
    }

    @ApiOperation(value="Retrieve a user account thanks to its email, if the user is registered in database")
    @GetMapping("/email/{email}")
    public UserDto getByEmail(@PathVariable String email) throws UserNotFoundException {
        return this.userService.findByEmail( email );
    }


}
