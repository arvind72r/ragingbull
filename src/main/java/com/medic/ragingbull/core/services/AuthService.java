/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.core.services;


import com.google.inject.Inject;
import com.medic.ragingbull.api.Access;
import com.medic.ragingbull.api.PasswordReset;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.AccessDao;
import com.medic.ragingbull.jdbi.dao.SessionsDao;
import com.medic.ragingbull.jdbi.dao.UsersDao;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final SessionsDao sessionsDao;
    private final UsersDao userDao;
    private final AccessDao accessDao;

    @Inject
    public AuthService(SessionsDao sessionsDao, UsersDao userDao, AccessDao accessDao) {
        this.sessionsDao = sessionsDao;
        this.userDao = userDao;
        this.accessDao = accessDao;
    }



    public void logoutUser(Session session) throws StorageException {
        try {
            int loggedOut = sessionsDao.logoutUser(session.getToken());
            if (loggedOut == 0) {
                LOGGER.error(String.format("Error logging out user session with email %s", session.getUserEmail()));
                throw new ResourceCreationException("Error creating session for the user. Please try again");
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error logging out session for user %s. Exception %s", session.getUserEmail(), e));
            throw new StorageException(e.getMessage());
        }
    }

    public PasswordReset resetPasswordLink(String userEmail) throws StorageException {

        try {
            User user = userDao.getByEmail(userEmail);
            DateTime expiry = SystemConstants.EXPIRY_TIME;
            if (user == null) {
                LOGGER.error(String.format("User with email %s not found in the system", userEmail));
                PasswordReset reset = new PasswordReset("-1", userEmail, expiry);
                return reset;
            }

            String accessId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.ACCESS);
            String resetId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.RESET);

            int count = accessDao.create(accessId, user.getId(), resetId, SystemConstants.AccessEntities.PASSWORD.name(), expiry.getMillis());

            PasswordReset reset = new PasswordReset(resetId, userEmail, expiry);
            if (count == 0) {
                LOGGER.error(String.format("Error entry for password reset for user %s.", userEmail));
                throw new ResourceCreationException("Error resetting password for the user. Please try again");
            }

            return reset;
        } catch(Exception e) {
            LOGGER.error(String.format("Error resetting password for user %s. Exception %s", userEmail, e));
            throw new StorageException(e.getMessage());
        }
    }

    public void resetPassword(String resetId, String email, String password) throws StorageException {
        try {
            Access access = accessDao.getByCode(resetId);

            if (access != null) {
                String hash = BCrypt.hashpw(password, BCrypt.gensalt());

                int passwordUpdated = userDao.updatePasswordByEmail(email, hash);

                if (passwordUpdated == 0) {
                    LOGGER.error(String.format("Error updating password for user %s.", email));
                    throw new ResourceUpdateException("Error updating password for user. Please try again");
                }
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error updating password for  user %s. Exception %s", email, e));
            throw new StorageException(e.getMessage());
        }
    }


}
