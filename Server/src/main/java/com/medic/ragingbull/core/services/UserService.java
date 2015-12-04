/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.core.notification.NotificationFactory;
import com.medic.ragingbull.exception.NotificationException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.*;
import com.medic.ragingbull.util.Time;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
@Singleton
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final DBI database;
    private final UsersDao userDao;
    private final AccessDao accessDao;
    private final SessionsDao sessionsDao;
    private final PractitionerDao practitionerDao;
    private final PharmacistDao pharmacistDao;
    private final NotificationFactory notificationFactory;

    @Inject
    public UserService(DBI database, NotificationFactory notificationFactory, UsersDao userDao, AccessDao accessDao, SessionsDao sessionsDao, PractitionerDao practitionerDao, PharmacistDao pharmacistDao) {
        this.database = database;
        this.notificationFactory = notificationFactory;
        this.userDao = userDao;
        this.accessDao = accessDao;
        this.sessionsDao = sessionsDao;
        this.practitionerDao = practitionerDao;
        this.pharmacistDao = pharmacistDao;
    }

    public RegistrationResponse register(User user) throws StorageException, ResourceCreationException, NotificationException {
        try {
            user.setId(com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER));

            String email = StringUtils.lowerCase(user.getEmail());
            String hashPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            int userCreated = userDao.createUser(user.getId(), user.getName(), email, hashPass, user.getPhone(), user.getInletType(), UserRoles.Role.NATIVE_USER.getRoleBit(), user.getPictureUrl(), user.getSex().name(), user.getDob().getMillis());

            if (userCreated == 0) {
                LOGGER.error(String.format("Error registering user %s. DB failed to save the record", user.getEmail()));
                throw new StorageException("Error registering user. Please try again");
            }

            // Generate AuthCode and notify user via SMS.
            Integer authCode = new Random().nextInt(SystemConstants.MAX_BOUND);
            notificationFactory.notifyUser(user.getPhone(), Notifiable.NotificationEvent.SIGN_UP, authCode.toString());

            String accessId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.ACCESS);
            long expiry = Time.getMillisAfterXDays(1);

            int inviteCreated = accessDao.create(accessId, user.getId(), authCode.toString(), SystemConstants.AccessEntities.INVITE.name(), expiry);

            if (inviteCreated == 0) {
                LOGGER.error(String.format("Error creating invite for user %s.", user.getEmail()));
                throw new StorageException("Error creating invite. Please try again");
            }
            return new RegistrationResponse(user.getId(), email, accessId, expiry);
        } catch (StorageException re) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", user.getEmail(), re));
            throw re;
        } catch (NotificationException re) {
            LOGGER.error(String.format("Error sending notification to the  user %s. Exception %s", user.getEmail(), re));
            throw re;
        } catch (final Exception e) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", user.getEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public Response addMember(Session session, String userId, Member member) throws StorageException, ResourceCreationException {
        try {
            String memberId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER);
            String hashPass = BCrypt.hashpw(SystemConstants.MEMBER_DEFAULT_PASSWORD, BCrypt.gensalt());
            if (StringUtils.isBlank(member.getPhone())) {
                // We take the phone no of the parent and prefix it with last for chars of the memberId to maintain uniqueness
                member.setPhone(memberId.substring(0, 5) + session.getPhone());
            }

            int memberCreated = userDao.createMember(memberId, userId, member.getName(), member.getEmail(), hashPass, member.getPhone(), "SELF", UserRoles.Role.NATIVE_MEMBER.getRoleBit());

            if (memberCreated == 0) {
                LOGGER.error(String.format("Error creating member under user %s.", session.getUserEmail()));
                throw new StorageException("Error creating member. Please try again");
            }
            return Response.ok().build();
        } catch (StorageException re) {
            throw re;
        } catch (Exception e) {
            LOGGER.error(String.format("Error creating member under user %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }


    public List<User> getMembers(String userId) throws ResourceCreationException {
        try {
            return userDao.getUsersByParent(userId);
        } catch (Exception e) {
            LOGGER.error(String.format("Error fetching members under user %s. Exception %s", userId, e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public Boolean resendInviteAuthCode(Session session, String userId) throws StorageException, NotificationException, ResourceCreationException {
        try {
            User user = new User();
            user.setEmail(session.getUserEmail());
            user.setPhone(session.getPhone());
            Access access = accessDao.getActiveByUserId(userId);
            if (access != null) {
                notificationFactory.notifyUser(user.getPhone(), Notifiable.NotificationEvent.RESEND_INVITE_CODE, access.getCode());
            } else {
                Integer authCode = new Random().nextInt(SystemConstants.MAX_BOUND);
                notificationFactory.notifyUser(user.getPhone(), Notifiable.NotificationEvent.RESEND_INVITE_CODE, authCode.toString());

                String inviteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.INVITE);
                long expiry = Time.getMillisAfterXDays(1);

                int inviteCreated = accessDao.create(inviteId, userId, authCode.toString(), SystemConstants.AccessEntities.INVITE.name(), expiry);
                if (inviteCreated == 0) {
                    LOGGER.error(String.format("Error creating invite for user %s.", user.getEmail()));
                    throw new StorageException("Error creating invite. Please try again");
                }
            }
            return true;
        } catch (StorageException re) {
            LOGGER.error(String.format("Error creating invite entry for the  user %s. Exception %s", session.getUserEmail(), re));
            throw re;
        } catch (NotificationException ne) {
            LOGGER.error(String.format("Error resending notification to the  user %s. Exception %s", session.getUserEmail(), ne));
            throw ne;
        } catch (Exception e) {
            LOGGER.error(String.format("Error registering user %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }


    }

    public Boolean approveInvite(final String authCode) throws StorageException, ResourceUpdateException {
        try {
            Access access = accessDao.getByCode(authCode);

            if (access == null) {
                return false;
            }
            int isApproved = userDao.approveUser(access.getUserId());
            if (isApproved == 0) {
                LOGGER.error(String.format("Error approving authCode %s. DB failed to update record", authCode));
                throw new StorageException("Error approving user. Please try again");
            }
            return true;
        } catch (StorageException se) {
            LOGGER.error(String.format("Error updating authCode for the  %s. Exception %s", authCode, se));
            throw se;
        } catch (Exception e) {
            LOGGER.error(String.format("Error approving auth code %s. Exception %s", authCode, e));
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    public Session generateSession(String username, String password) throws StorageException {
        try {
            User user = userDao.getByEmail(username);
            if (user != null && BCrypt.checkpw(password, user.getHash())) {
                return getSession(user);
            }

        } catch (Exception e) {
            LOGGER.error(String.format("Error creating session for user %s. Exception %s", username, e));
            throw new StorageException(e.getMessage());

        }
        return null;
    }

    public Session getSession(User user) throws ResourceCreationException {
        List<Session> sessions = sessionsDao.getActiveSessionsPerUserEmail(user.getEmail());

        if (sessions.size() > 0) {
            sessions.get(0).setIsUserValid(user.getVerified());
            sessions.get(0).setPhone(user.getPhone());
            return sessions.get(0);
        }

        String sessionId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.SESSION);
        DateTime expiry = new DateTime().plus(Time.getMillisAfterXDays(1));
        DateTime createdAt = new DateTime();
        Session loggedInUserSession = new Session(sessionId, user.getId(), user.getEmail(), user.getRole(), expiry, createdAt);
        loggedInUserSession.setIsUserValid(user.getVerified());

        int sessionCreated = sessionsDao.createSession(sessionId, user.getId(), user.getEmail(), user.getRole(), expiry.getMillis());
        if (sessionCreated == 0) {
            LOGGER.error(String.format("Error creating session for user %s.", user.getEmail()));
            throw new ResourceCreationException("Error creating session for the user. Please try again");
        }

        return loggedInUserSession;
    }

    public User getUser(Session session, String userId, Boolean hydrated) throws ResourceUpdateException, StorageException {
        try {
            User user = userDao.getById(userId);

            if (user == null) {
                LOGGER.error(String.format("Error fetching details for user %s.", session.getUserEmail()));
                throw new StorageException("Error fetching user details. Pls try again");
            }

            if (hydrated) {
                if ((user.getRole() & UserRoles.Role.NATIVE_PHARMACIST.getRoleBit()) == UserRoles.Role.NATIVE_PHARMACIST.getRoleBit()) {
                    Practitioner practitioner = practitionerDao.getByUserId(userId);
                    user.setPractitioner(practitioner);
                }
                if ((user.getRole() & UserRoles.Role.NATIVE_PHARMACIST.getRoleBit()) == UserRoles.Role.NATIVE_PHARMACIST.getRoleBit()) {
                    Pharmacist pharmacist = pharmacistDao.getByUserId(userId);
                    user.setPharmacist(pharmacist);
                }
            }
            return user;
        } catch (StorageException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(String.format("Error fetching details for user %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceUpdateException(e.getMessage());
        }

    }

    public Boolean updateUser(Session session, String userId, String field, Map<String, String> data) throws ResourceUpdateException, StorageException {
        try {
            Handle h = database.open();
            int update = h.update("UPDATE USER set " + field + " = ?  where id = ?", data.get("value"), userId);
            h.close();
            if (update == 0) {
                LOGGER.error(String.format("Error updating field %s for user %s.", field, session.getUserEmail()));
                throw new StorageException("Error updating user data. Please try again");
            }
            return true;
        } catch (StorageException re) {
            LOGGER.error(String.format("Error updating password for user %s. Exception %s", session.getUserEmail(), re));
            throw re;
        } catch (Exception e) {
            LOGGER.error(String.format("Error updating password for user %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    public Boolean updatePassword(Session session, String userId, Map<String, String> data) throws ResourceUpdateException, StorageException {
        try {
            String currentPasswordHash = userDao.getHashById(userId);

            if (BCrypt.checkpw(data.get("password"), currentPasswordHash) && StringUtils.equals(data.get("password1"), data.get("password2"))) {
                // Matching password
                String latestPasswordHash = BCrypt.hashpw(data.get("password1"), BCrypt.gensalt());

                int passwordUpdated = userDao.updatePasswordById(userId, latestPasswordHash);

                if (passwordUpdated == 0) {
                    LOGGER.error(String.format("Error updating password for user %s.", session.getUserEmail()));
                    throw new StorageException("Error updating user password. Please try again");
                }
                return true;
            }
            return false;
        } catch (StorageException re) {
            LOGGER.error(String.format("Error updating password for user %s. Exception %s", session.getUserEmail(), re));
            throw re;
        } catch (Exception e) {
            LOGGER.error(String.format("Error updating password for user %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceUpdateException(e.getMessage());
        }
    }
}
