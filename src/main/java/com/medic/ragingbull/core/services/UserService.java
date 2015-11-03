/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.core.notification.NotificationFactory;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.InviteDao;
import com.medic.ragingbull.api.Invite;
import com.medic.ragingbull.api.RegistrationResponse;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.jdbi.dao.SessionDao;
import com.medic.ragingbull.jdbi.dao.UserDao;
import com.medic.ragingbull.util.Time;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
@Singleton
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;
    private final InviteDao inviteDao;
    private final SessionDao sessionDao;
    private final NotificationFactory notificationFactory;


    @Inject
    public UserService(UserDao userDao, InviteDao inviteDao, SessionDao sessionDao, NotificationFactory notificationFactory) {
        this.userDao = userDao;
        this.inviteDao = inviteDao;
        this.sessionDao = sessionDao;
        this.notificationFactory = notificationFactory;
    }


    @Transaction
    public RegistrationResponse register(final User user) throws StorageException {
        try {
            String userId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER);
            String email = StringUtils.lowerCase(user.getEmail());
            String hashPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            int userCreated = userDao.createUser(userId, user.getName(), email, hashPass, user.getContactNo(), user.getInletType(), new Integer(20),new Integer(20), user.getPictureUrl());

            if (userCreated == 0 ) {
                LOGGER.error(String.format("Error registering user %s.", user.getEmail()));
                throw new ResourceCreationException("Error registering user. Please try again");
            }
            int authCode = new Random().nextInt(SystemConstants.MAX_BOUND);
            notificationFactory.notifyUser(user, Notifiable.Mode.SMS, authCode);

            String inviteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.INVITE);
            long expiry = Time.getMillisAfterXDays(1);

            int inviteCreated = inviteDao.createInvite(inviteId, userId, authCode, expiry);

            if (inviteCreated == 0 ) {
                LOGGER.error(String.format("Error creating invite for user %s.", user.getEmail()));
                throw new ResourceCreationException("Error creating invite. Please try again");
            }
            return new RegistrationResponse(user.getName(), email, inviteId, expiry);
        } catch (final Exception e) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", user.getEmail(), e));
            throw new StorageException(e.getMessage());
        }
    }


    public Response approveInvite(final String inviteId) {
        Invite invite = inviteDao.getInviteById(inviteId);
        int isApproved = userDao.approveUser(invite.getUserId());
        if (isApproved > 0) {
            return Response.ok().build();
        }
        return Response.status(HttpStatus.FORBIDDEN_403).build();
    }

    public Response getUserInvite(String inviteId) {
        Invite invite = inviteDao.getInviteById(inviteId);
        return Response.ok(invite).build();
    }

    public User verifyUser(String email, String password) {
        User user = userDao.getByEmail(email);
        String hashedPassword = userDao.getHash(email);
        if (BCrypt.checkpw(password, hashedPassword)) {
            return user;
        }
        return null;
    }

    public Session generateSession(String username, String password) throws StorageException {
        try {
            User user = userDao.getByEmail(username);
            if (user != null && BCrypt.checkpw(password, user.getHash())) {
                List<Session> sessions = sessionDao.getActiveSessionsPerUserEmail(username);

                if (sessions.size() > 0) {
                    sessions.get(0).setIsUserValid(user.getVerified());
                    return sessions.get(0);
                }

                String sessionId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.SESSION);
                DateTime expiry = new DateTime().plus(Time.getMillisAfterXDays(1));
                DateTime createdAt = new DateTime();
                Session loggedInUserSession = new Session(sessionId, user.getEmail(), user.getId(), expiry, createdAt);
                loggedInUserSession.setIsUserValid(user.getVerified());

                int sessionCreated = sessionDao.createSession(sessionId, user.getId(), user.getEmail(), expiry.getMillis());
                if (sessionCreated == 0) {
                    LOGGER.error(String.format("Error creating session for user %s.", user.getEmail()));
                    throw new ResourceCreationException("Error creating session for the user. Please try again");
                }

                return loggedInUserSession;
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error creating session for user %s. Exception %s", username, e));
            throw new StorageException(e.getMessage());

        }
        return null;
    }

    public Response getAllUserInvites(String userEmail) {
        List<Invite> invite = inviteDao.getInviteByEmail(userEmail);
        return Response.ok(invite).build();
    }
}
