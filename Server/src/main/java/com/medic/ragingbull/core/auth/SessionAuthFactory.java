/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.auth;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.medic.ragingbull.api.Session;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class SessionAuthFactory<T> extends AuthFactory<String, Session> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthFactory.class);
    public final static String AUTH_HEADER_NAME = "Auth-Token";

    @Context
    private HttpServletRequest request;
    private boolean required = false;


    /**
     * Default constructor
     *
     * @param authenticator
     */
    public SessionAuthFactory(final Authenticator<String, Session> authenticator) {
        this(false, authenticator);
    }

    /**
     * Constructor for optionally required
     *
     * @param required
     * @param authenticator
     */
    public SessionAuthFactory(final boolean required,
                              final Authenticator<String, Session> authenticator) {
        super(authenticator);

        this.required = required;
    }


    @Override
    public void setRequest(final HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public AuthFactory<String, Session> clone(final boolean required) {
        return new SessionAuthFactory<>(required, authenticator());
    }

    @Override
    public Class<Session> getGeneratedClass() {
        return Session.class;
    }

    @Override
    public Session provide() {
        if (request == null)
        {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credentials are required to access this resource.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build());
        }

        String sessionAuthHeader = request.getHeader(javax.ws.rs.core.HttpHeaders.AUTHORIZATION);

        String sessionHeader = request.getHeader(AUTH_HEADER_NAME);


        final boolean sessionHeaderValid = !Strings.isNullOrEmpty(sessionHeader)&& !sessionHeader.toLowerCase().equals("false");
        final boolean sessionAuthValid = !Strings.isNullOrEmpty(sessionAuthHeader)&& !sessionAuthHeader.toLowerCase().equals("false");

        if (sessionHeader != null || sessionAuthHeader != null) {
            try {

                //warn if both are valid and not equal
                if (sessionAuthValid && sessionHeaderValid && !sessionAuthHeader.equals(sessionHeader))
                    LOGGER.warn("Session for header and cookie were both valid but set to different values!");

                // Use the header first, then the cookie
                final String sessionId = sessionHeaderValid?sessionHeader:sessionAuthHeader;

                final Optional<Session> session = authenticator().authenticate(sessionId);

                if (session.isPresent()) {
                    return session.get();
                }
            } catch (AuthenticationException e) {
                LOGGER.warn("Error authenticating credentials");
            }
        }

        if(required) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credentials are required to access this resource.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build());
        }

        return null;
    }
}