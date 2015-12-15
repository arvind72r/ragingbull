/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.NotificationException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class UserAccessService {

    private UserService userService;

    @Inject
    public UserAccessService(UserService userService) {
        this.userService = userService;
    }

    public Response register(User user) throws NotificationException, StorageException, ResourceCreationException {
        Response response = userService.register(user);
        return response;
    }

    public Response resendInviteAuthCode(Session session, String userId) throws NotificationException, StorageException, ResourceCreationException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }
        userService.resendInviteAuthCode(session, userId);
        return Response.ok().build();
    }

    public Response addMember(Session session, String userId, Member user) throws ResourceCreationException, StorageException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }

        if ((session.getRole() & UserRoles.Permissions.USER_MEMBER_ADD.getBitValue()) == UserRoles.Permissions.USER_MEMBER_ADD.getBitValue()) {
            return userService.addMember(session, userId, user);
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_ADD_MEMBER_CODE).build();
    }

    public Response getMembers(Session session, String userId) throws ResourceCreationException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }
        if ((session.getRole() & UserRoles.Permissions.USER_MEMBER_READ.getBitValue()) == UserRoles.Permissions.USER_MEMBER_READ.getBitValue()) {
            List<User> members = userService.getMembers(userId);
            return Response.ok().entity(members).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response approveInvite(String authCode) throws ResourceUpdateException, StorageException {
        return  userService.approveInvite(authCode)? Response.ok().build(): Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    public Response update(Session session, String userId, String field, Map<String, String> data) throws StorageException, ResourceUpdateException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }

        if ((session.getRole() & UserRoles.Permissions.USER_MODIFY.getBitValue()) == UserRoles.Permissions.USER_MODIFY.getBitValue()) {
            if (StringUtils.equals(field, "password")) {
                return userService.updatePassword(session, userId, data) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).entity(ErrorMessages.INVALID_PASSWORD_USER_RESOURCE_CODE).build();
            } else {
                return userService.updateUser(session, userId, field, data)? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).entity(ErrorMessages.INVALID_DETAILS_USER_RESOURCE_CODE).build();
            }
        }

        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_MODIFY_CODE).build();

    }

    public Response getUser(Session session, String userId, Boolean hydrated) throws StorageException, ResourceUpdateException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }

        if ((session.getRole() & UserRoles.Permissions.USER_READ.getBitValue()) == UserRoles.Permissions.USER_READ.getBitValue()) {
            User user = userService.getUser(session, userId, hydrated);
            return Response.ok().entity(user).build();
        }

        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_READ_CODE).build();
    }

    public Response getUserDashBoard(Session session, String userId) {
        DashBoard dashBoard = userService.getDashBoard(session, userId);
        return Response.ok().entity(dashBoard).build();
    }
}
