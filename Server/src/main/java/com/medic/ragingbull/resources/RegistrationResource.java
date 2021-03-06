/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.access.service.UserAccessService;
import com.medic.ragingbull.exception.*;
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
    public Response registerUser(@Valid User user) throws StorageException, NotificationException, ResourceCreationException, DuplicateEntityException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("RegisterUser: Registering a new user. UserName: %s", user.getName());
        }
        Response response = userAccessService.register(user);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("RegisterUser: Successfully registered user. UserName: %s", user.getName());
        }
        return response;
    }

    @POST
    @Path("/oauth")
    public Response registerOAuthUser(@Valid User user) throws StorageException, NotificationException, ResourceCreationException, DuplicateEntityException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("RegisterOAuthUser: Registering a new oauth user. UserName: %s. InletType: %s", user.getName(), user.getInletType());
        }
        Response response = userAccessService.registerOAuth(user);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("RegisterOAuthUser: Registering a new oauth user. UserName: %s. InletType: %s", user.getName(), user.getInletType());
        }
        return response;
    }

    @POST
    @Path("/{id}/approve")
    public Response approveRegisteredUser(@PathParam("id") final String authCode) throws StorageException, ResourceUpdateException {
        Response response = userAccessService.approveInvite(authCode);
        return response;
    }
}

