/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.EntityUser;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.PractitionerLocationAccessService;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerLocationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationResource.class);

    private PractitionerLocationAccessService practitionerLocationAccessService;


    @Inject
    public PractitionerLocationResource(PractitionerLocationAccessService practitionerLocationAccessService) {
        this.practitionerLocationAccessService = practitionerLocationAccessService;
    }

    @GET
    @Path("/{locationId}")
    public Response getPractitionerLocation(@Auth Session session, @PathParam("locationId") String locationId) throws StorageException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Practitioner location: Fetching all location details id: %s. Request by: %s", locationId, session.getUserEmail()));
        }
        return practitionerLocationAccessService.getPractitionerLocation(session, locationId);
    }

    @GET
    @Path("/{locationId}/users")
    public Response getUsers(@Auth Session session, @PathParam("locationId") String locationId) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Practitioner location users: Fetching all users for a location id: %s. Request by: %s", locationId, session.getUserEmail()));
        }
        Response response = practitionerLocationAccessService.getUsers(session, locationId);
        return response;
    }

    @GET
    @Path("/{locationId}/users/{userId}")
    public Response getUser(@Auth Session session, @PathParam("locationId") String locationId, @PathParam("userId") String userId) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Practitioner location user: Fetching user for a location id: %s. User id: %s. Request by: %s", locationId, userId, session.getUserEmail()));
        }
        Response response = practitionerLocationAccessService.getUser(session, locationId, userId);
        return response;
    }

    @PUT
    @Path("/{locationId}/users")
    public Response addUsers(@Auth Session session, @PathParam("locationId") String locationId, @Valid List<EntityUser> entityUsers) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Practitioner location user: Creating a user for a location id: %s. Request by: %s", locationId, session.getUserEmail()));
        }
        Response response = practitionerLocationAccessService.addUsers(session, locationId, entityUsers);
        return response;
    }

    @POST
    @Path("/{locationId}/consultation")
    public Response addConsultation(@Auth Session session, @PathParam("locationId") String locationId,  @Valid Consultation consultation) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Consultation: Creating consultation for a location id: %s. Request by: %s", locationId, session.getUserEmail()));
        }
        Response response = practitionerLocationAccessService.createConsultation(session, locationId, consultation);
        return response;
    }

}
