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
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
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

    public void loginUser(Session session) throws StorageException {
        try {
            int sessionCreated = sessionDao.createSession(session.getToken(), session.getUserId(), session.getUserEmail(), session.getExpiry().getMillis());
            if (sessionCreated == 0) {
                LOGGER.error(String.format("Error creating session for user %s.", session.getUserEmail()));
                throw new ResourceCreationException("Error creating session for the user. Please try again");
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error creating session for user %s. Exception %s", session.getUserEmail(), e));
            throw new StorageException(e.getMessage());

        }

    }

    public void logoutUser(Session session) throws StorageException {
        try {
            int loggedOut = sessionDao.logoutUser(session.getToken());
            if (loggedOut == 0) {
                LOGGER.error(String.format("Error logging out user session with email %s", session.getUserEmail()));
                throw new ResourceCreationException("Error creating session for the user. Please try again");
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error logging out session for user %s. Exception %s", session.getUserEmail(), e));
            throw new StorageException(e.getMessage());
        }
    }
}
