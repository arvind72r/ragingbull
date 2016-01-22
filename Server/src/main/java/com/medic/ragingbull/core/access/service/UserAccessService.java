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
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.ImageService;
import com.medic.ragingbull.core.services.PrescriptionService;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class UserAccessService {

    private UserService userService;
    private PrescriptionService prescriptionService;
    private ImageService imageService;

    @Inject
    public UserAccessService(UserService userService, PrescriptionService prescriptionService, ImageService imageService)
    {
        this.userService = userService;
        this.prescriptionService = prescriptionService;
        this.imageService = imageService;
    }

    public Response register(User user) throws NotificationException, StorageException, ResourceCreationException, DuplicateEntityException {
        Session session = userService.register(user);
        return Response.ok().entity(session).cookie(new NewCookie(SystemConstants.SESSION_COOKIE_NAME, session.getToken())).build();
    }

    public Response registerOAuth(User user) throws ResourceCreationException, StorageException {
        Session session = userService.registerOAuth(user);
        return Response.ok().entity(session).cookie(new NewCookie(SystemConstants.SESSION_COOKIE_NAME, session.getToken())).build();
    }

    public Response resendInviteAuthCode(Session session, String userId) throws NotificationException, StorageException, ResourceCreationException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }
        userService.resendInviteAuthCode(session, userId);
        return Response.ok().build();
    }

    public Response addMember(Session session, String userId, Member user) throws ResourceCreationException, StorageException, DuplicateEntityException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }

        if ((session.getRole() & UserRoles.Permissions.USER_MEMBER_ADD.getBitValue()) == UserRoles.Permissions.USER_MEMBER_ADD.getBitValue()) {
            Member member = userService.addMember(session, userId, user);
            return Response.ok().entity(member).build();
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
        return  userService.approveInvite(authCode)? Response.ok().build(): Response.status(Response.Status.CONFLICT).build();
    }

    public Response update(Session session, String userId, String field, Map<String, String> data) throws StorageException, ResourceUpdateException {
        if (!StringUtils.equals(session.getUserId(), userId)) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_RESOURCE_CODE).build();
        }

        if ((session.getRole() & UserRoles.Permissions.USER_MODIFY.getBitValue()) == UserRoles.Permissions.USER_MODIFY.getBitValue()) {
            if (StringUtils.equals(field, "phone")) {
                boolean updatePhone = userService.updatePhone(userId, data);
                if (updatePhone) {
                    return Response.ok().build();
                } else {
                    return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_MODIFY_CODE).build();
                }
            }
            else if (StringUtils.equals(field, "password")) {
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


    public Response getUserCart(Session session, String userId) {
        return null;
    }

    public Response addItemToCart(Session session, String userId, List<CartItem> item) {
        return null;
    }

    public Response orderCart(Session session, String userId) {
        return null;
    }

    public Response emptyUserCart(Session session, String userId) {
        return null;
    }

    public Response removeCartItem(Session session, String userId) {
        return null;
    }

    public Response getPrescriptions(Session session, String userId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            JSONObject allPrescriptionsResponse = new JSONObject();
            List<PrescriptionResponse> prescriptions = prescriptionService.getPrescriptions(session, userId);
            List<ImageResponse> prescriptionImages = imageService.getPrescriptionImages(session, userId);
            allPrescriptionsResponse.put("app", prescriptions);
            allPrescriptionsResponse.put("offline", prescriptionImages);
            return Response.ok().entity(allPrescriptionsResponse).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_USER_READ_CODE).build();
    }
}
