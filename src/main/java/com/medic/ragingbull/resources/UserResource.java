/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);
    private UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    public User getUser(@Auth Session session, @PathParam("id") final String userId, @QueryParam("hydrated")Optional<Boolean> hydrated) throws StorageException {
        User user = userService.getUser(session, userId, hydrated.or(Boolean.FALSE));
        return user;
    }

    @PUT
    @Path("/{id}")
    public User updateUser(@Auth Session session, @PathParam("id") final String userId, User user) throws StorageException {
        return userService.updateUser(session, userId, user);
    }
}
