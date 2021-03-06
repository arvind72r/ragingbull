/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.LoginResponse;
import com.medic.ragingbull.api.PasswordReset;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.AuthService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
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
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Login: Logging in user. Request by: %s", session.getUserEmail()));
        }
        LoginResponse response = new LoginResponse(session.getToken(), session.getUserEmail(), session.getIsUserValid(), session.getExpiry());

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Login: Successfully logged in user. Request by: %s", session.getUserEmail()));
        }
        return Response.ok().entity(response).cookie(new NewCookie(SystemConstants.SESSION_COOKIE_NAME, session.getToken())).build();
    }

    @Path("/logout")
    @POST
    public Response logout(@Auth Session session) throws StorageException {
        authService.logoutUser(session);
        return Response.ok().build();
    }

    @Path("/reset")
    @POST
    public Response getResetLink(@QueryParam("id") @Valid @Email String userEmail) throws StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Resetting password link for user. Request by: %s", userEmail);
        }

        PasswordReset reset = authService.resetPasswordLink(userEmail);
        return Response.ok().entity(reset).build();
    }

    @Path("/reset/{id}")
    @POST
    public Response resetPassword(@PathParam("id") String resetId, @FormParam("userEmail") String userEmail, @FormParam("password") String password) throws StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reset Password: Resetting for user. Request by: %s", userEmail);
        }
        authService.resetPassword(resetId, userEmail, password);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reset Password: Success reset password for user. Request by: %s", userEmail);
        }
        return Response.ok().build();
    }
}
