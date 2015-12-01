/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.RegistrationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.NotificationException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);
    private UserService userService;

    @Inject
    public RegistrationResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    public Response resendInviteAuthCode(@Auth Session session, @PathParam("id") final String userId) throws StorageException, NotificationException, ResourceCreationException {
        Response response = userService.resendInviteAuthCode(session, userId);
        return response;
    }

    @POST
    public RegistrationResponse registerUser(@Valid User user) throws StorageException, NotificationException, ResourceCreationException {
        RegistrationResponse response =  userService.register(user);
        return response;
    }

    @POST
    @Path("/{id}/approve")
    public Response approveRegisteredUser(@PathParam("id") final String authCode) throws StorageException {
        Response response = userService.approveInvite(authCode);
        return response;
    }
}

