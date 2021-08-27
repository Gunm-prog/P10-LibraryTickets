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


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID=-2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY=5 * 60 * 60;


   /* //retrieve username from jwt token
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
    }*/


    //generate token for user
    public String generateToken(UserPrincipal userPrincipal) {
        //generate personalized claims
      /*  Map<String, Object> claims=new HashMap<>();
        claims.put("userId", userPrincipal.getUserId());
        claims.put("role", userPrincipal.getAuthorities().toString());*/
        //use doGenerateToken to build and return the token
        return doGenerateToken( userPrincipal );
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(UserPrincipal userPrincipal) {
        return JWT.create()
                .withClaim("role",userPrincipal.getAuthorities().toString())
                .withClaim("userId",userPrincipal.getUserId())
                .withClaim("lastname", userPrincipal.getLastname())
                .withClaim("firstname", userPrincipal.getFirstname())
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date( System.currentTimeMillis() ))
                .withExpiresAt(new Date( System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 ))
                .sign( Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
        /*return JWT.builder().setClaims( claims ).setSubject( subject ).setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 ) )
                .signWith( SignatureAlgorithm.HS512, secret ).compact();*/
    }



/*
    public Long getCurrentUserId(String accessToken){

        return JWT.decode( accessToken ).getClaim( "userId" ).asLong();
    }
*/


  /*  public void isCurrentUser(RequestHeader requestHeader, Long id){

        String accessToken = requestHeader.value();
        System.out.println(accessToken);
        Long currentId = getCurrentUserId( accessToken );

        if (id != currentId){
            throw new UnauthorizedException("not the current user");
        }
    }*/


    /*//generate token for user
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
    public Boolean validateToken(String token, UserPrincipal userPrincipal) {
        final String username=getUsernameFromToken( token );
        return (username.equals( userPrincipal.getUsername() ) && !isTokenExpired( token ));
    }*/
}