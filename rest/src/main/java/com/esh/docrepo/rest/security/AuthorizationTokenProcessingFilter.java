package com.esh.docrepo.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

/**
 * This filter performs the user authentication through the authorization token created on user log in.
 * 
 * @author armfergom
 *
 */
public class AuthorizationTokenProcessingFilter extends GenericFilterBean {

    // The user service used to get the user information. Implementation is UserDaoImpl
    private final UserDetailsService userService;

    public AuthorizationTokenProcessingFilter(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get http request
        HttpServletRequest httpRequest = this.getAsHttpRequest(request);

        // Extract the authorization token. Fail if not existing
        String authToken = this.extractAuthTokenFromRequest(httpRequest);
        // Extract the username from the token
        String userName = TokenUtils.getUserNameFromToken(authToken);

        // If there is no user name, authentication will not be done
        if (userName != null) {
            // Get user details from database using the user name
            UserDetails userDetails = this.userService.loadUserByUsername(userName);

            // Validate the token by creating it again and comparing it with the one sent. Also check the expiration
            // date.
            if (TokenUtils.validateToken(authToken, userDetails)) {
                // If token validation went well, perform authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Call next filter in the chain
        chain.doFilter(request, response);
    }

    /**
     * Transforms the request into a http request
     * 
     * @param request
     * @return
     */
    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

    /**
     * Extract the authorization token from the header.
     * 
     * Specifically, from the X-Auth-Token header. If it is not found in the header, look for it in the parameters of
     * the http request
     * 
     * @param httpRequest
     * @return
     */
    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        // Get token from header
        String authToken = httpRequest.getHeader("X-Auth-Token");

        // If token not found get it from request parameter
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }

        return authToken;
    }
}