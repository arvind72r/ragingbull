/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.RegistrationResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.services.UserService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class RegistrationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);
    private final UserService userService;

    @Inject
    public RegistrationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response registerUser(@Valid User user) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Registering a new user with email: %s", user.getEmail()));
        }

        RegistrationResponse registeredUser =  userService.register(user);
        return Response.ok().entity(registeredUser).build();
    }
}
