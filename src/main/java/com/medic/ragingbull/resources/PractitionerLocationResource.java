/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.permissions.Privileges;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.core.services.PractitionerLocationService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerLocationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationResource.class);

    private PractitionerLocationService practitionerLocationService;

    private ConsultationService consultationService;

    @Inject
    public PractitionerLocationResource(PractitionerLocationService practitionerLocationService, ConsultationService consultationService) {
        this.practitionerLocationService = practitionerLocationService;
        this.consultationService = consultationService;
    }

    @GET
    @Path("/{locationId}")
    public PractitionerLocationResponse getPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId) throws StorageException {
        PractitionerLocationResponse response = practitionerLocationService.getPractitioner(session, practitionerId, locationId);
        return response;
    }

    @GET
    @Path("/{locationId}/users")
    public Response getUsers(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationService.getUsers(session, practitionerId, locationId);
        return response;
    }

    @GET
    @Path("/{locationId}/users/{userId}")
    public Response getUser(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, @PathParam("userId") String userId) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationService.getUser(session, practitionerId, locationId, userId);
        return response;
    }

    @PUT
    @Path("/{locationId}/users")
    public Response addUsers(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, List<Map<String, String>> users) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationService.addUsers(session, practitionerId, locationId, users);
        return response;
    }

}
