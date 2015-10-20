/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.Invite;
import com.naked.penguin.api.RegistrationResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.config.Ids;
import com.naked.penguin.jdbi.dao.InvitesDao;
import com.naked.penguin.jdbi.dao.UserDao;
import com.naked.penguin.util.Time;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Singleton
public class UserService {

    private final UserDao userDao;
    private final InvitesDao inviteDao;

    @Inject
    public UserService(UserDao userDao, InvitesDao invitesDao) {
        this.userDao = userDao;
        this.inviteDao = invitesDao;
    }
    public RegistrationResponse register(final User user) {
        // 1. Create a user
        // 2. Add entry in invite table
        try {
            String userId = com.naked.penguin.util.Ids.generateId(Ids.Type.USER);
            String email = StringUtils.lowerCase(user.getEmail());
            String hashPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            String name = user.getName();
            Boolean isVerified = Boolean.FALSE;
            Boolean isNative = Boolean.TRUE;

            int userCreated = userDao.createUser(userId, email, user.getName(), hashPass, isVerified, isNative);

            if (userCreated == 0 ) {
                // Error created user, fallback
            }

            String inviteId = com.naked.penguin.util.Ids.generateId(Ids.Type.INVITE);
            long expiry = Time.getMillisAfterXDays(1);

            int inviteCreated = inviteDao.createInvite(inviteId, userId, expiry);

            if (inviteCreated == 0 ) {
                // Push the invite to a queue so that it can be created again
            }

            return new RegistrationResponse(name, email, inviteId, expiry);
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
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

    public Response getUserInvite(User user, String emailId) {
        Invite invite = inviteDao.getInviteByEmail(user.getId());
        // Check expiry of invite
        return Response.ok(invite.getInviteId()).build();
    }

    public User verifyUser(String email, String password) {
        User user = userDao.getByEmail(email);
        String hashedPassword = userDao.getHash(email);
        if (BCrypt.checkpw(password, hashedPassword)) {
            return user;
        }
        return null;
    }
}
