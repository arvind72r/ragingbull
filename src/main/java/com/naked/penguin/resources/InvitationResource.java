/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.User;
import com.naked.penguin.services.UserService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/invite")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class InvitationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationResource.class);
    private final UserService userService;

    @Inject
    public InvitationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/{inviteId}")
    public Response approveInvite(@PathParam("inviteId") String inviteId) {
        return userService.approveInvite(inviteId);
    }

    @GET
    @Path("/{emailId}")
    public Response getUserInvite(@Auth User user, @PathParam("emailId") String emailId) {
        return userService.getUserInvite(user, emailId);
    }

}
