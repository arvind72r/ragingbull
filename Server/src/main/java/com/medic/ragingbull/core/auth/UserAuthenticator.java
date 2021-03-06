/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.core.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.ext.Provider;

/**
 * Created by Vamshi Molleti
 */
@Singleton
@Provider
public class UserAuthenticator implements Authenticator<BasicCredentials, Session> {

    private final UserService userService;

    @Inject
    public UserAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Session> authenticate(BasicCredentials userCredentials) throws AuthenticationException {
        Session loggedInUserSession = null;
        try {
            loggedInUserSession = userService.generateSession(userCredentials.getUsername(), userCredentials.getPassword());
        } catch (StorageException e) {
            throw  new AuthenticationException("Error authenticating user. Please try again");
        }
        return Optional.fromNullable(loggedInUserSession);
    }
}
