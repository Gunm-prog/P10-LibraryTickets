package com.emilie.Lib7.Config;

/*
import com.auth0.jwt.JWT;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenUtil {

    public static final String HEADER = "access_token";


    private JwtTokenUtil(){}

    */
/**
     * This method generate a cookie with token as value
     *
     * @param token access token to store
     * @return a cookie
     *//*

    public static Cookie generateCookie (String token){

        Cookie cookie = new Cookie(HEADER, token);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(999999); // 12 days
        cookie.setDomain("localhost");
        cookie.setPath("/");

        return cookie;
    }

    */
/**
     * This method delete cookie stored
     *
     * @param httpServletResponse the HttpServletResponse with cookie to delete
     *//*

    public static void clear(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(HEADER, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    */
/**
     * This method delete cookie stored
     *
     * @param httpServletRequest HttpServletResponse with cookie to use
     * @param name the name of the value to find
     *//*

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static String getUsernameFromJWT(String token){
        String username = JWT.decode(token).getSubject();
        return username;
    }

    public static Integer getUserIdFromJWT(String token){
        return JWT.decode(token).getClaim("userId").asInt();
    }


}
*/


import com.emilie.Lib7.Models.Entities.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID=-2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY=5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken( token, Claims::getSubject );
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken( token, Claims::getExpiration );
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=getAllClaimsFromToken( token );
        return claimsResolver.apply( claims );
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey( secret ).parseClaimsJws( token ).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration=getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }

    //generate token for user
    public String generateToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("userId", userPrincipal.getUserId());
        return doGenerateToken( claims, userPrincipal.getUsername() );
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims( claims ).setSubject( subject ).setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 ) )
                .signWith( SignatureAlgorithm.HS512, secret ).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken( token );
        return (username.equals( userDetails.getUsername() ) && !isTokenExpired( token ));
    }
}