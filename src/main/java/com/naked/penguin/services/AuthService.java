/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.services;


import com.google.inject.Inject;
import com.naked.penguin.api.User;
import com.naked.penguin.config.Ids;
import com.naked.penguin.config.SystemContants;
import com.naked.penguin.jdbi.dao.SessionDao;

/**
 * Created by Vamshi Molleti
 */
public class AuthService {

    private final SessionDao sessionDao;

    @Inject
    public AuthService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public String login(User user) {
        String sessionId = com.naked.penguin.util.Ids.generateId(Ids.Type.SESSION);
        int sessionCreated = sessionDao.createSession(sessionId, user.getId(), user.toString(), SystemContants.EXPIRY_TIME);
        if (sessionCreated == 0) {
            // throw error
        }
        return sessionId;
    }
}
