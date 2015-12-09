package com.esh.docrepository.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.esh.docrepository.dto.Token;
import com.esh.docrepository.security.TokenUtils;

@Component
@Path("/user")
public class UserResource {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    /**
     * Authenticates a user and creates an authentication token.
     * 
     * @param username
     *            The name of the user.
     * @param password
     *            The password of the user.
     * @return A transfer containing the authentication token.
     */
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Token authenticate(@FormParam("username") String username, @FormParam("password") String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        // If authentication fails, a BadCredentialsException will be thrown and the UnauthorizeEntryPoint will be
        // executed
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload user as password of authentication principal will be null after authorization and password is needed
        // for token generation
        UserDetails userDetails = this.userService.loadUserByUsername(username);

        // Create and return a token for this user
        return new Token(TokenUtils.createToken(userDetails));
    }
}
