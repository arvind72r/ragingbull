/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.Role;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.core.notification.NotificationFactory;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.*;
import com.medic.ragingbull.util.Time;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
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

    private final DBI database;
    private final UserDao userDao;
    private final InviteDao inviteDao;
    private final SessionDao sessionDao;
    private final PractitionerDao practitionerDao;
    private final PharmacistDao pharmacistDao;
    private final NotificationFactory notificationFactory;

    @Inject
    public UserService(DBI database, NotificationFactory notificationFactory, UserDao userDao, InviteDao inviteDao, SessionDao sessionDao, PractitionerDao practitionerDao, PharmacistDao pharmacistDao) {
        this.database = database;
        this.notificationFactory = notificationFactory;
        this.userDao = userDao;
        this.inviteDao = inviteDao;
        this.sessionDao = sessionDao;
        this.practitionerDao = practitionerDao;
        this.pharmacistDao = pharmacistDao;
    }

    public RegistrationResponse register(final User user) throws StorageException {
        return registerUser(user, Role.NATIVE_USER);
    }

    public RegistrationResponse registerAnon(User user) throws StorageException {
        user.setEmail(user.getPhone());
        return registerUser(user, Role.ANON);
    }

    private RegistrationResponse registerUser(User user, Role role) throws StorageException {
        try {
            if (role == Role.NATIVE_USER) {
                user.setId(com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER));
            } else {
                user.setId(com.medic.ragingbull.util.Ids.generateId(Ids.Type.ANON_USER));
            }
            String email = StringUtils.lowerCase(user.getEmail());
            String hashPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            int userCreated = userDao.createUser(user.getId(), user.getName(), email, hashPass, user.getPhone(), user.getInletType(), user.getRole(), user.getPictureUrl());

            if (userCreated == 0 ) {
                LOGGER.error(String.format("Error registering user %s.", user.getEmail()));
                throw new ResourceCreationException("Error registering user. Please try again");
            }

            // Create Practitioner if data provided
            if (user.getPractitioner() != null) {
                String practitionerId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER);

                int practitionerCreated = practitionerDao.createPractitioner(practitionerId, user.getId(), user.getPractitioner().getDescription(), user.getPractitioner().getPrimaryContact(), user.getPractitioner().getSecondaryContact(), user.getPractitioner().getPrimaryId(), user.getPractitioner().getSecondaryId(), user.getPractitioner().getRegistrationId(), user.getPractitioner().getRegistrationAuthority(), user.getPractitioner().getLicense());

                if (practitionerCreated == 0) {
                    LOGGER.error(String.format("Error creating a practitioner with email %s", user.getEmail()));
                    throw new ResourceCreationException(String.format("Error creating a practitioner with email %s", user.getEmail()));
                }
            } else if (user.getPharmacist() != null) {
                // Create Pharmacist if data provided
                String pharmacistId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PHARMACY);

                int pharmacistCreated = pharmacistDao.createPharmacist(pharmacistId, user.getId(), user.getPharmacist().getDescription(), user.getPharmacist().getPrimaryContact(), user.getPharmacist().getSecondaryContact(), user.getPharmacist().getPrimaryId(), user.getPharmacist().getSecondaryId(), user.getPharmacist().getRegistrationId(), user.getPharmacist().getRegistrationAuthority(), user.getPharmacist().getLicense());
                if (pharmacistCreated == 0) {
                    LOGGER.error(String.format("Error creating a pharmacist with email %s", user.getEmail()));
                    throw new ResourceCreationException(String.format("Error creating a pharmacist with email %s", user.getEmail()));
                }
            }

            // Generate AuthCode and notify user via SMS.
            int authCode = new Random().nextInt(SystemConstants.MAX_BOUND);
            notificationFactory.notifyUser(user, Notifiable.Mode.SMS, authCode);

            String inviteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.INVITE);
            long expiry = Time.getMillisAfterXDays(1);

            int inviteCreated = inviteDao.createInvite(inviteId, user.getId(), authCode, expiry);

            if (inviteCreated == 0 ) {
                LOGGER.error(String.format("Error creating invite for user %s.", user.getEmail()));
                throw new ResourceCreationException("Error creating invite. Please try again");
            }
            return new RegistrationResponse(user.getId(), email, inviteId, expiry);
        } catch (final Exception e) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", user.getEmail(), e));
            throw new StorageException(e.getMessage());
        }
    }



    public Response resendInviteAuthCode(Session session, String userId) throws StorageException {
        try {
            if (StringUtils.equals(session.getUserId(), userId)) {
                User user = new User();
                user.setEmail(session.getUserEmail());
                user.setPhone(session.getPhone());
                Invite invite = inviteDao.getInviteByUserId(userId);
                if (invite != null) {
                    notificationFactory.notifyUser(user, Notifiable.Mode.SMS, invite.getCode());
                    return Response.ok().build();
                } else {
                    int authCode = new Random().nextInt(SystemConstants.MAX_BOUND);
                    notificationFactory.notifyUser(user, Notifiable.Mode.SMS, authCode);

                    String inviteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.INVITE);
                    long expiry = Time.getMillisAfterXDays(1);

                    int inviteCreated = inviteDao.createInvite(inviteId, userId, authCode, expiry);
                    if (inviteCreated == 0 ) {
                        LOGGER.error(String.format("Error creating invite for user %s.", user.getEmail()));
                        throw new ResourceCreationException("Error creating invite. Please try again");
                    }
                    return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
                }
            } else {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", session.getUserEmail(), e));
            throw new StorageException(e.getMessage());
        }


    }

    public Response approveInvite(final String authCode) {
        Invite invite = inviteDao.getInviteByCode(authCode);
        int isApproved = userDao.approveUser(invite.getUserId());
        if (isApproved > 0) {
            return Response.ok().build();
        }
        return Response.status(HttpStatus.FORBIDDEN_403).build();
    }

    public Response getUserInvite(String inviteId) {
        Invite invite = inviteDao.getInviteByUserId(inviteId);
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
                return getSession(user);
            }

        } catch(Exception e) {
            LOGGER.error(String.format("Error creating session for user %s. Exception %s", username, e));
            throw new StorageException(e.getMessage());

        }
        return null;
    }

    public Session getSession(User user) throws ResourceCreationException {
        List<Session> sessions = sessionDao.getActiveSessionsPerUserEmail(user.getEmail());

        if (sessions.size() > 0) {
            sessions.get(0).setIsUserValid(user.getVerified());
            sessions.get(0).setPhone(user.getPhone());
            return sessions.get(0);
        }

        String sessionId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.SESSION);
        DateTime expiry = new DateTime().plus(Time.getMillisAfterXDays(1));
        DateTime createdAt = new DateTime();
        Session loggedInUserSession = new Session(sessionId, user.getEmail(), user.getId(), user.getRole(), expiry, createdAt);
        loggedInUserSession.setIsUserValid(user.getVerified());

        int sessionCreated = sessionDao.createSession(sessionId, user.getId(), user.getEmail(), user.getRole(), expiry.getMillis());
        if (sessionCreated == 0) {
            LOGGER.error(String.format("Error creating session for user %s.", user.getEmail()));
            throw new ResourceCreationException("Error creating session for the user. Please try again");
        }

        return loggedInUserSession;
    }

    public User getUser(Session session, String userId, Boolean hydrated) {
        if (StringUtils.equals(session.getUserId(), userId)) {
            User user = userDao.getById(userId);

            if (hydrated) {
                if ((user.getRole() & Role.PRACTITIONER.getBitValue()) == Role.PRACTITIONER.getBitValue()) {
                    Practitioner practitioner = practitionerDao.getByUserId(userId);
                    user.setPractitioner(practitioner);
                } else if((user.getRole() & Role.PHARMACIST.getBitValue()) == Role.PHARMACIST.getBitValue()) {
                    Pharmacist pharmacist = pharmacistDao.getByUserId(userId);
                    user.setPharmacist(pharmacist);
                }
            }
            return user;
        }
        return null;
    }

    public User updateUser(Session session, String userId, User user) {
        if (StringUtils.equals(session.getUserId(), userId)) {
            userDao.updateInfo(userId,  user.getName(), user.getEmail(), user.getPictureUrl());
            return user;
        }
        return null;
    }


    public Response updateUser(Session session, String userId, String field, String data) {
        if (StringUtils.equals(session.getUserId(), userId)) {
            Handle h = database.open();
            int update = h.update("UPDATE USER set " + field + " = ?  where id = ?",  data, userId);
            h.close();;
            return Response.ok().build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(new HttpError(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE, ErrorMessages.FORBIDDEN_USER_RESOURCE_MESSAGE)).build();
    }
}
