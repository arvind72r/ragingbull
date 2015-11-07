/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private UserService userService;

    public enum Fields {
        name("name"), email("email");

        private String value;
        private Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    public User getUser(@Auth Session session, @PathParam("id") String userId, @QueryParam("hydrated")Optional<Boolean> hydrated) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        User user = userService.getUser(session, userId, hydrated.or(Boolean.FALSE));
        return user;
    }

    @PUT
    @Path("/{id}/modify/{field}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateUser(@Auth Session session, @PathParam("id")  String userId, @PathParam("field") Fields field, String data) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return userService.updateUser(session, userId, field.name(), data);
    }
}
