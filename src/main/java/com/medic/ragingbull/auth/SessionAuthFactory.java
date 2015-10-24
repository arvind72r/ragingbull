/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.auth;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.medic.ragingbull.api.Session;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: Mike Kurdziel
 * Date: 4/19/14
 */
public class SessionAuthFactory<T> extends AuthFactory<String, Session> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthFactory.class);
    public final static String SESSION_COOKIE_NAME = "sessionId";

    @Context
    private HttpServletRequest request;
    private final boolean required = false;


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

        //this.required = required;
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

        String sessionHeader = request.getHeader(javax.ws.rs.core.HttpHeaders.AUTHORIZATION);
        String sessionCookie = null;

        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                final String name = cookie.getName();
                if (name != null && name.equals(SESSION_COOKIE_NAME)) {
                    sessionCookie = cookie.getValue();
                }
            }
        }
        final boolean sessionHeaderValid = Strings.isNullOrEmpty(sessionHeader) == false && !sessionHeader.toLowerCase().equals("false");
        final boolean sessionCookieValid = Strings.isNullOrEmpty(sessionCookie) == false && !sessionCookie.toLowerCase().equals("false");

        if (sessionHeader != null || sessionCookie != null) {
            try {

                //warn if both are valid and not equal
                if (sessionCookieValid && sessionHeaderValid && !sessionCookie.equals(sessionHeader))
                    LOGGER.warn("Session for header and cookie were both valid but set to different values!");

                // Use the header first, then the cookie
                final String sessionId = sessionHeaderValid?sessionHeader:sessionCookie;

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