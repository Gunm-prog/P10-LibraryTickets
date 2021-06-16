package com.emilie.Lib7.Authentication.Service;

import com.emilie.Lib7.Authentication.Repository.UserRepository;
import com.emilie.Lib7.Authentication.entities.User;
import com.emilie.Lib7.Authentication.entities.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userAUthenticationRepository) {
        this.userRepository= userAUthenticationRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user= userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for "+ username + ".");
        }
        return new UserPrincipal( user );
    }
}

