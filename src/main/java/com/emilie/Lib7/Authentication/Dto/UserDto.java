package com.emilie.Lib7.Authentication.Dto;


import com.emilie.Lib7.Authentication.Repository.UserRepository;
import com.emilie.Lib7.Authentication.Service.UserDetailsServiceImpl;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserDto extends UserDetailsServiceImpl {


    private String username;
    private String password;


    public UserDto(UserRepository userAUthenticationRepository) {
        super( userAUthenticationRepository );
    }
}
