/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.LoginResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/oauth")
public class OAuthResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);
    private OAuthService oAuthService;

    @Inject
    public OAuthResource(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GET
    @Path("/{provider}")
    public Response getAuthorizationUrl(@PathParam("provider") final OAuthService.Providers provider) {
        String authorizationUrl = oAuthService.getAuthorizationUrl(provider);
        return Response.ok().entity(authorizationUrl).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{provider}/callback")
    public Response providerOAuthCallback(@PathParam("provider") final OAuthService.Providers provider, @QueryParam("code") String authCode) throws Exception {
        Session session = oAuthService.getProfileInfoFromOAuth(provider, authCode);
        LoginResponse response = new LoginResponse(session.getToken(), session.getUserEmail(), session.getIsUserValid(), session.getExpiry());
        return Response.ok().entity(response).cookie(new NewCookie(SystemConstants.SESSION_COOKIE_NAME, session.getToken())).build();
    }
}
