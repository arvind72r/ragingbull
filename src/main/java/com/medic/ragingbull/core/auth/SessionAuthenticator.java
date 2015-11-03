/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAuthenticator<String, Session> implements Authenticator<String, Session> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthenticator.class);

    public Optional<Session> authenticate(final String sessionId) throws AuthenticationException {
        try {
            final Session session = findSessionById(sessionId);
            if (session != null) {
                if(isValidCredential(session)) {
                    return  Optional.of(session);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Storage exception getting session for authentication");
        }
        throw new AuthenticationException("Access Denied");

    }

    private boolean isValidCredential(Session session) {
        return false;
    }

    private Session findSessionById(String sessionId) {
        return null;
    }

}