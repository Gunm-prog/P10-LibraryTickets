package com.emilie.Lib7.Authentication.Web;

import com.emilie.Lib7.Authentication.Config.JwtResponse;
import com.emilie.Lib7.Authentication.Config.JwtTokenUtil;
import com.emilie.Lib7.Authentication.Dto.UserJwtDto;
import com.emilie.Lib7.Authentication.Repository.UserJwtRepository;
import com.emilie.Lib7.Authentication.Service.UserDetailsServiceImpl;
import com.emilie.Lib7.Authentication.entities.UserJwt;
import com.emilie.Lib7.Exceptions.AddressNotFoundException;
import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private UserJwtRepository userJwtRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserJwtDto userJwtDto) throws Exception {

        authenticate(userJwtDto.getUsername(), userJwtDto.getPassword());

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userJwtDto.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("test");
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws UserAlreadyExistException {
        Optional<UserJwt> optionalUserJwt=userJwtRepository.findByUsername( userDto.getUsername() );
        System.out.println(optionalUserJwt);
        if (optionalUserJwt.isPresent()) {
            throw new UserAlreadyExistException( "Email already exists" );
        }
        if(userDto.getAddressDto() == null){
            throw new AddressNotFoundException( "Address not found" );
        }

        String hashPassword = bCryptPasswordEncoder.encode( userDto.getPassword() );
        UserJwt userJwt = new UserJwt();
        userJwt.setPassword( hashPassword );
        userJwt.setUsername( userDto.getEmail() );
        userJwt.setActive( true );
        userJwt.setRoles("ROLE_CUSTOMER");
        userJwt = userDetailsServiceImpl.save(userJwt);


        UserDto userDto1=userServiceImpl.save( userDto );
        return ResponseEntity.status( HttpStatus.CREATED ).build();


    }
}














