/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.services.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by Vamshi Molleti
 */
public class SessionAuthProvider implements Authenticator<String, Session> {

    @Context
    private HttpServletRequest request;

    private  UserService userService;

    @Inject
    public SessionAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Session> authenticate(String s) throws AuthenticationException {
        System.out.println("String s is "+ s);
        return Optional.absent();
    }

}
