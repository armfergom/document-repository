package com.esh.docrepo.rest.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.esh.docrepo.rest.model.User;

@Service
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long ONE_HOUR = 1000 * 60 * 60;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(@Value("${token.secret}") String secret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    /**
     * Sets the generated token in the header of the response to send back to the client
     * 
     * @param response
     * @param authentication
     */
    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        // Gets yser from the authentication details
        final User user = authentication.getDetails();
        // Sets the expiration
        user.setExpires(System.currentTimeMillis() + ONE_HOUR);
        // Set token in header
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }

    /**
     * Gets the token from the header of the request and retrieve the user from it, creating an user authentication to
     * be returned
     * 
     * @param request
     * @return
     */
    public Authentication getAuthentication(HttpServletRequest request) {
        // Extract token from header
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            // Extract user from token
            final User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                // Build and return user authentication
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
