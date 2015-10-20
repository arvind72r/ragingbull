/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.User;
import com.naked.penguin.services.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.ext.Provider;

/**
 * Created by Vamshi Molleti
 */
@Singleton
@Provider
public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserService userService;

    public UserAuthenticator() {
        userService = null;
    }

    public UserAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials userCredentials) throws AuthenticationException {
        User loggedInUser = userService.verifyUser(userCredentials.getUsername(), userCredentials.getPassword());
        return Optional.fromNullable(loggedInUser);
    }
}
