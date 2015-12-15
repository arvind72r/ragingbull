/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAuthenticator implements Authenticator<String, Session> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthenticator.class);

    private UserService userService;

    public SessionAuthenticator(UserService userService) {
        this.userService = userService;
    }

    public Optional<Session> authenticate(final String sessionId) throws AuthenticationException {
        try {
            final Session session = findSessionById(sessionId);
            if (session != null) {
                return Optional.of(session);
            }
        } catch (Exception e) {
            LOGGER.error("Storage exception getting session for authentication");
        }
        throw new AuthenticationException("Access Denied");

    }

    private Session findSessionById(String sessionId) {
        return userService.getSession(sessionId);
    }

}