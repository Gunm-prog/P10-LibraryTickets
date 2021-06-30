package com.emilie.Lib7.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import com.emilie.Lib7.Services.impl.UserDetailsServiceImpl;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        // 1. Check for token in request
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        // 2. If there is no token the user won't be authenticated.
        if(token == null) {
            logger.warn("token is null");
            chain.doFilter(request, response);
            return;
        }

        token =token.replace(JwtProperties.TOKEN_PREFIX,"");
        System.out.println(token);

        try {

            // 3. Validate the token
            DecodedJWT jwt = JWT.require( Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            System.out.println(username);
            if(username != null) {

                // 4. Retrieve the user into bdd with username
                UserPrincipal userPrincipal = userDetailsServiceImpl.loadUserByUsername(username);

                // 5. Create auth object
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());

                // 6. Authenticate the user
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // In case of failure. Make sure user won't be authenticated
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }







   /* @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(UserDetailsServiceImpl userDetailsServiceImpl, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsServiceImpl=userDetailsServiceImpl;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse,
                                     FilterChain filterChain) throws ServletException, IOException {

    }


    public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


        public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
            super( authenticationManager );
        }
*/
       /* @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            // Read the Authorization header, where the JWT token should be
            String header=request.getHeader( JwtProperties.HEADER_STRING );

            // If header does not contain BEARER or is null delegate to Spring impl and exit
            if (header == null || !header.startsWith( JwtProperties.TOKEN_PREFIX )) {
                chain.doFilter( request, response );
                return;
            }

            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication=getUsernamePasswordAuthentication( request );
            SecurityContextHolder.getContext().setAuthentication( authentication );

            // Continue filter execution
            chain.doFilter( request, response );
        }

        private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {


            String token=request.getHeader( JwtProperties.HEADER_STRING )
                    .replace( JwtProperties.TOKEN_PREFIX, "" );

            if (token != null) {
                // parse the token and validate it
                String username=JWT.require( HMAC512( JwtProperties.SECRET.getBytes() ) )
                        .build()
                        .verify( token )
                        .getSubject();

                // Search in the DB if we find the user by token subject (username)
                // If so, then grab user details and create spring auth token using username, pass, authorities/roles
                if (username != null) {
                    String authoritiesJwt=JWT.require( HMAC512( JwtProperties.SECRET.getBytes() ) )
                            .build()
                            .verify( token )
                            .getClaim( "authorities" ).toString();
                    List<String> authoritiesList=Arrays.asList( authoritiesJwt.split( "," ) );
                    List<GrantedAuthority> authorities=new ArrayList<>();
                    authoritiesList.forEach( p -> {
                        GrantedAuthority authority=new SimpleGrantedAuthority( p );
                        authorities.add( authority );
                    } );

                    return new UsernamePasswordAuthenticationToken( username, null, authorities );
                }
                return null;
            }
            return null;
        }
    }
}*/


 /*       @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws ServletException, IOException {

            final String requestTokenHeader=request.getHeader( "Authorization" );

            String username=null;
            String jwtToken=null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if ((requestTokenHeader != null) && requestTokenHeader.startsWith( "Bearer " )) {
                jwtToken=requestTokenHeader.substring( 7 );
                try {
                    username=jwtTokenUtil.getUsernameFromToken( jwtToken );
                } catch (IllegalArgumentException e) {
                    System.out.println( "Unable to get JWT Token" );
                } catch (ExpiredJwtException e) {
                    System.out.println( "JWT Token has expired" );
                }
            } else {
                logger.warn( "JWT Token does not begin with Bearer String" );
            }

            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername( username );

                // if token is valid configure Spring Security to manually set
                // authentication
                if (jwtTokenUtil.validateToken( jwtToken, (UserPrincipal) userDetails )) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities() );
                    usernamePasswordAuthenticationToken
                            .setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
                }
            }
            chain.doFilter( request, response );
        }

    }*/
}
