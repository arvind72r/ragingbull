/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.services;


import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.Ids;
import com.medic.ragingbull.config.SystemContants;
import com.medic.ragingbull.jdbi.dao.SessionDao;

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
        String sessionId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.SESSION);
        int sessionCreated = sessionDao.createSession(sessionId, user.getId(), user.toString(), SystemContants.EXPIRY_TIME);
        if (sessionCreated == 0) {
            // throw error
        }
        return sessionId;
    }
}
