package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Config.JwtTokenUtil;
import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Dtos.UserJwtDto;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.impl.UserDetailsServiceImpl;
import com.emilie.Lib7.Services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestBody UserJwtDto userJwtDto) throws Exception {

        authenticate( userJwtDto.getUsername(), userJwtDto.getPassword() );

        UserPrincipal userPrincipal=userDetailsServiceImpl.loadUserByUsername( userJwtDto.getUsername() );
        System.out.println(userPrincipal);
        return jwtTokenUtil.generateToken( userPrincipal );
       /* System.out.println( "test" );
        System.out.println( token );
        return ResponseEntity.ok( new JwtResponse( token ) );*/
    }

  /*  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserJwtDto userJwtDto) throws Exception {

        authenticate(userJwtDto.getUsername(), userJwtDto.getPassword());

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userJwtDto.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("test");
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }*/

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(value="/register/employee")
    /*@PreAuthorize( "hasAnyRole('ADMIN', 'EMPLOYEE')" )*/
    public ResponseEntity<?> registerEmployee(@RequestBody UserDto userDto) throws UserAlreadyExistException{
        userDto.setActive( true );
        userDto.setRoles("EMPLOYEE");
        String hashPassword = bCryptPasswordEncoder.encode( userDto.getPassword() );
        userDto.setPassword( hashPassword );
        UserDto userDto1 = userServiceImpl.save( userDto );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value="/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody UserDto userDto) throws UserAlreadyExistException {

        String hashPassword = bCryptPasswordEncoder.encode( userDto.getPassword() );

        userDto.setPassword( hashPassword );
        userDto.setActive( true );
        userDto.setRoles("CUSTOMER");

        UserDto userDto1=userServiceImpl.save( userDto );
        return ResponseEntity.status( HttpStatus.CREATED ).build();


    }
}














