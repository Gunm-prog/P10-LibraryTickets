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











/*import com.emilie.Lib7.Authentication.Config.JwtTokenUtil;
import com.emilie.Lib7.Authentication.entities.LoginUserInfo;
import com.emilie.Lib7.Authentication.entities.UserJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class JwtAuthenticationController {

    private final FeignProxy feignProxy;
    private static final String LOGIN_VIEW = "login";
    private static final String HOME_VIEW = "home";
    private static final String REGISTRATION_VIEW = "register";
    private static final String REDIRECT_LOGIN_VIEW = "redirect:/login";
    private static final String REDIRECT_HOME_VIEW = "redirect:/home";
    private static final String CUSTOMER_ATT = "customer";
    private static final String USERNAME_FIELD = "username";
    private static final String BAD_CREDENTIALS_MSG = "Mauvais login/mot de passe";

    @Autowired
    public JwtAuthenticationController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("user", new LoginUserInfo());
        return LOGIN_VIEW;
    }

    @PostMapping("/login")
    public String login(@Valid LoginUserInfo loginUserInfoDto, Model model, HttpServletResponse response,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.addError(new FieldError(CUSTOMER_ATT, USERNAME_FIELD, BAD_CREDENTIALS_MSG));
            model.addAttribute("user", loginUserInfoDto);
            return LOGIN_VIEW;
        }
        ResponseEntity responseEntity;
        try {
            responseEntity = feignProxy.login(loginUserInfoDto);
        } catch (Exception e){
            model.addAttribute("user", loginUserInfoDto);
            return LOGIN_VIEW;
        }
        ResponseEntity responseEntity = feignAuthProxy.login(loginUserInfoDto);
        if (responseEntity.getStatusCode().is4xxClientError()){
            model.addAttribute("user", loginUserInfoDto);
            return LOGIN_VIEW;
        }
        String token = responseEntity.getHeaders().getFirst("Authorization").replace("Bearer ", "");
        Cookie cookie = JwtTokenUtil.generateCookie(token);
        response.addCookie(cookie);
        return REDIRECT_HOME_VIEW;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new LoginUserInfo());
        return REGISTRATION_VIEW;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserJwt userJwt){
        ResponseEntity responseEntity = feignProxy.register(userJwt);
        if (responseEntity.getStatusCode().equals( HttpStatus.UNAUTHORIZED)){
            return REGISTRATION_VIEW;
        }
        return REDIRECT_HOME_VIEW;
    }

    @GetMapping({"/home", "/", "/index"})
    public String home(HttpServletRequest request, HttpSession session){
        String value = JwtTokenUtil.getValue(request, JwtTokenUtil.HEADER);
        if (value != null){
            session.setAttribute("logged", "true");
        }
        return HOME_VIEW;
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session){
        JwtTokenUtil.clear(response);
        session.invalidate();
        return REDIRECT_LOGIN_VIEW;
    }
}*/









