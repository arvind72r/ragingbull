/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.PractitionerAccessService;
import com.medic.ragingbull.core.access.service.PractitionerLocationAccessService;
import com.medic.ragingbull.exception.DuplicateEntityException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerResource.class);

    private PractitionerAccessService practitionerAccessService;
    private PractitionerLocationAccessService practitionerLocationAccessService;

    @Inject
    public PractitionerResource(PractitionerAccessService practitionerAccessService, PractitionerLocationAccessService practitionerLocationAccessService) {
        this.practitionerAccessService = practitionerAccessService;
        this.practitionerLocationAccessService = practitionerLocationAccessService;
    }

    @GET
    public Response getPractitioners(@Auth Session session) throws StorageException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Fetching all practitioners in system. Request by: %s", session.getUserEmail()));
        }
        Response response = practitionerAccessService.getPractitioners(session);
        return response;
    }

    @GET
    @Path("/{id}")
    public Response getPractitioner(@Auth Session session, @PathParam("id") String practitionerId) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Practitioner: Fetching practitioner details id: %s. Request by: %s", practitionerId, session.getUserEmail()));
        }
        return practitionerAccessService.getPractitioner(session, practitionerId);
    }

    @POST
    public Response createPractitioner(@Auth Session session, @Valid Practitioner practitioner) throws StorageException, ResourceCreationException, DuplicateEntityException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Practitioner: Received request to create practitioner. Request by: %s", session.getUserEmail()));
        }

        Response response = practitionerAccessService.createPractitioner(session, practitioner);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Practitioner: Successfully created practitioner. Request by: %s", session.getUserEmail()));
        }
        return response;
    }

    @POST
    @Path("/{id}/location")
    public Response addPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId,  @Valid PractitionerLocation practitionerLocation) throws StorageException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Creating practitioner location: Received request to create practitioner location. PractitionerId: %s. Request by: %s", practitionerId, session.getUserEmail()));
        }
        Response response  = practitionerLocationAccessService.createPractitionerLocation(session, practitionerId, practitionerLocation);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Creating practitioner location: Successfully created practitioner location. PractitionerId: %s. Request by: %s", practitionerId, session.getUserEmail()));
        }
        return response;
    }
}
