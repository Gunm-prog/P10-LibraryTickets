package com.emilie.Lib7.Authentication.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserInfo {
    private String username;
    private String password;
}
