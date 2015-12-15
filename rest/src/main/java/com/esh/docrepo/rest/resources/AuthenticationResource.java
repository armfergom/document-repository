/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.esh.docrepo.rest.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.esh.docrepo.rest.model.Token;
import com.esh.docrepo.rest.security.TokenUtils;

@Component
@Path("/user")
public class AuthenticationResource {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Create and return a token for this user
        return new Token(TokenUtils.createToken(userDetails));
    }

}
