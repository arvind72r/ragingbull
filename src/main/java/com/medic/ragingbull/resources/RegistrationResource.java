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
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.core.services.UserService;
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
    public Response getAllRegistrationStatuses(@Auth Session session) {
        Response response = userService.getAllUserInvites(session.getUserEmail());
        return response;
    }

    @GET
    @Path("/{id}")
    public Response registrationStatus(@Auth Session session, @PathParam("id") final String registrationId) {
        Response response = userService.getUserInvite(registrationId);
        return response;
    }

    @POST
    public RegistrationResponse registerUser(@Valid User user) throws StorageException {
        RegistrationResponse response =  userService.register(user);
        return response;
    }

    @POST
    @Path("/{id}/approve")
    public Response approveRegisteredUser(@PathParam("id") final String registrationId) {
        Response response = userService.approveInvite(registrationId);
        return response;
    }


}
