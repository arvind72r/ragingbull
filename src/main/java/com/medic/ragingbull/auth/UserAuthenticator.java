/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.services.UserService;
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
        Session loggedInUserSession = userService.generateSession(userCredentials.getUsername(), userCredentials.getPassword());
        return Optional.fromNullable(loggedInUserSession);
    }
}
