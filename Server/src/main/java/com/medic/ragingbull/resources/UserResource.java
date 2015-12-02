/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Member;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.access.service.UserAccessService;
import com.medic.ragingbull.core.services.UserService;
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
        name("name"), email("email"), password ("password");

        private String value;
        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    @Inject
    public UserResource(UserService userService, UserAccessService userAccessService) {
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

    @POST
    @Path("/{id}/member")
    public Response addMember(@Auth Session session, @PathParam("id") String userId, @Valid Member member) throws ResourceCreationException, StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }

        Response response = userAccessService.addMember(session, userId, member);
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
}
