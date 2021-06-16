package com.emilie.Lib7.Authentication.Config;

public class JwtProperties {
    public static final String SECRET = "SecretKeyJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
