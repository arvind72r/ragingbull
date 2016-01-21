/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.api.Member;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.UserAccessService;
import com.medic.ragingbull.exception.DuplicateEntityException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private UserAccessService userAccessService;

    public enum Fields {
        name("name"), email("email"), password ("password"), phone("phone"), dob("dob");

        private String value;
        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    @Inject
    public UserResource(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @GET
    @Path("/{id}")
    public Response getUser(@Auth Session session, @PathParam("id") String userId, @QueryParam("hydrated")Optional<Boolean> hydrated) throws StorageException, ResourceUpdateException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.getUser(session, userId, hydrated.or(Boolean.FALSE));
    }

    @GET
    @Path("/{id}/dashboard")
    public Response getUser(@Auth Session session, @PathParam("id") String userId) throws StorageException, ResourceUpdateException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("GetUser: Getting Dashboard. RequestBy: %s", session.getUserEmail()));
        }

        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }

        Response response =  userAccessService.getUserDashBoard(session, userId);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("GetUser: Successfully gt Dashboard. RequestBy: %s", session.getUserEmail()));
        }
        return response;
    }

    @POST
    @Path("/{id}/member")
    public Response addMember(@Auth Session session, @PathParam("id") String userId, @Valid Member member) throws ResourceCreationException, StorageException, DuplicateEntityException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("AddMember: Received request to add member. MemberName: %s. RequestBy: %s", member.getName(), session.getUserEmail()));
        }
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = userAccessService.addMember(session, userId, member);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("AddMember: Successfully created member. MemberName: %s.  RequestBy: %s", member.getName(), session.getUserEmail()));
        }
        return response;
    }

    @GET
    @Path("/{id}/member")
    public Response getMembers(@Auth Session session, @PathParam("id") String userId) throws ResourceCreationException, StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.getMembers(session, userId);
    }

    @PUT
    @Path("/{id}/modify/{field}")
    public Response updateUser(@Auth Session session, @PathParam("id")  String userId, @PathParam("field") Fields field, Map<String, String> data) throws StorageException, ResourceUpdateException, IOException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.update(session, userId, field.getValue(), data);
    }

    @GET
    @Path("/{id}/prescriptions")
    public Response getPrescription(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.getPrescriptions(session, userId);
    }
    @GET
    @Path("/{id}/cart")
    public Response getUserCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.getUserCart(session, userId);
    }

    @PUT
    @Path("/{id}/cart")
    public Response addItemToCart(@Auth Session session, @PathParam("id") String userId, @Valid List<CartItem> items) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.addItemToCart(session, userId, items);
    }

    @DELETE
    @Path("/{id}/cart/{itemId}")
    public Response removeCartItem(@Auth Session session, @PathParam("id") String userId){
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.removeCartItem(session, userId);
    }

    @DELETE
    @Path("/{id}/cart")
    public Response emptyCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.emptyUserCart(session, userId);
    }

    @POST
    @Path("/{id}/cart/order")
    public Response orderCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userAccessService.orderCart(session, userId);
    }
}
