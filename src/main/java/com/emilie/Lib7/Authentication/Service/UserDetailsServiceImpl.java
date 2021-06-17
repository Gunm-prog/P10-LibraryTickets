package com.emilie.Lib7.Authentication.Service;

import com.emilie.Lib7.Authentication.Repository.UserJwtRepository;
import com.emilie.Lib7.Authentication.entities.UserJwt;
import com.emilie.Lib7.Authentication.entities.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserJwtRepository userJwtRepository;

    public UserDetailsServiceImpl(UserJwtRepository userAuthenticationRepository) {
        this.userJwtRepository= userAuthenticationRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserJwt> userJwt= userJwtRepository.findByUsername(username);
        if (userJwt.isEmpty()) {
            throw new UsernameNotFoundException("No user found for "+ username + ".");
        }
        return new UserPrincipal( userJwt.get() );
    }

    public UserJwt save(UserJwt userJwt) {
       return this.userJwtRepository.save( userJwt );
    }
}

