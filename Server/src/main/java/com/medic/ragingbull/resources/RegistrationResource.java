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
import com.medic.ragingbull.core.access.service.UserAccessService;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.NotificationException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
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
    private UserAccessService userAccessService;

    @Inject
    public RegistrationResource(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @GET
    @Path("/{id}")
    public Response resendInviteAuthCode(@Auth Session session, @PathParam("id") String userId) throws StorageException, NotificationException, ResourceCreationException {
        if (StringUtils.equals(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = userAccessService.resendInviteAuthCode(session, userId);
        return response;
    }

    @POST
    public RegistrationResponse registerUser(@Valid User user) throws StorageException, NotificationException, ResourceCreationException {
        RegistrationResponse response = userAccessService.register(user);
        return response;
    }

    @POST
    @Path("/{id}/approve")
    public Response approveRegisteredUser(@PathParam("id") final String authCode) throws StorageException, ResourceUpdateException {
        Response response = userAccessService.approveInvite(authCode);
        return response;
    }
}

