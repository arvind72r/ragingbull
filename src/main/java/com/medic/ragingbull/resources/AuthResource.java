/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.LoginResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.SystemConstants;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.services.AuthService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    private  AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @Path("/login")
    @POST
    public Response login(@Auth Session session) throws StorageException {
        authService.loginUser(session);
        LoginResponse response = new LoginResponse(session.getToken(), session.getUserEmail(), session.getExpiry());
        return Response.ok().entity(response).cookie(new NewCookie(SystemConstants.SESSION_COOKIE_NAME, session.getToken())).build();
    }

    @Path("/logout")
    @POST
    public Response logout(@Auth Session session) throws StorageException {
        authService.logoutUser(session);
        return Response.ok().build();
    }
}
