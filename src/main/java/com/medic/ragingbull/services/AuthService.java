/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.services;


import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.Ids;
import com.medic.ragingbull.config.SystemConstants;
import com.medic.ragingbull.jdbi.dao.SessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final SessionDao sessionDao;

    @Inject
    public AuthService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public String login(User user) {
        String sessionId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.SESSION);
        int sessionCreated = sessionDao.createSession(sessionId, user.getId(), user.toString(), SystemConstants.EXPIRY_TIME);
        if (sessionCreated == 0) {
            // throw error
        }
        return sessionId;
    }

    public void saveSession(Session session) {
        int sessionCreated = sessionDao.createSession(session.getToken(), session.getUserId(), session.getUserEmail(), SystemConstants.EXPIRY_TIME);
        if (sessionCreated == 0) {
            LOGGER.error(String.format("Error creating session "));
        }
    }
}
