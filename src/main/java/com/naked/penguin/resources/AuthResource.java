/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.LoginResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.config.SystemContants;
import com.naked.penguin.services.AuthService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class AuthResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);
    private final AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public LoginResponse login(@Auth User user) {
        String sessionId = authService.login(user);
        return new LoginResponse(sessionId, SystemContants.EXPIRY_TIME, user);
    }
}
