package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import io.swagger.annotations.ApiOperation;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users/")
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

    /*@ApiOperation( value= "Retrieve a user account from library card number, if user is registered in database" )
    @GetMapping ("/card-number")
    public UserDto getBycardNumber(@PathVariable(value="card_number") String cardNumber){
        return this.userService.findByCardNumber(cardNumber);
    }*/

    @ApiOperation( value="Retrieve a user account thanks to its last name, if the user is registered in database")
    @GetMapping("/user-LastName")
    public UserDto getByLastName(@PathVariable(value="last_name") String lastName) throws UserNotFoundException {
        /*userDto.setLastName( lastName );*/
        return this.userService.findByLastName(lastName);
    }

    @ApiOperation( value="Retrieve a user account thanks to its email, if the user is registered in database")
    @GetMapping("/email")
    public UserDto getByEmail(@PathVariable(value="email") String email) throws UserNotFoundException {
        return this.userService.findByEmail(email);
    }

  /*  @DeleteMapping("/{id}")
    public UserDto deleteById(@PathVariable(value="id") Long id){

        return this.userService.deleteById( id );
    }*/

}
