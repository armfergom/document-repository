package com.esh.docrepository.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * {@link AuthenticationEntryPoint} that rejects all requests with an unauthorized error message.
 * 
 * @author armfergom
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    /**
     * This is called if the user was not authenticated. The authentication is done in
     * {@link AuthenticationTokenProcessingFilter}
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
    }

}