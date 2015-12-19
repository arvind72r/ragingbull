/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.service.PractitionerAccessService;
import com.medic.ragingbull.core.access.service.PractitionerLocationAccessService;
import com.medic.ragingbull.core.services.PractitionerLocationService;
import com.medic.ragingbull.core.services.PractitionerService;
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching all practitioners in system. Request by: %s", session.getUserEmail()));
        }
        Response response = practitionerAccessService.getPractitioners(session);
        return response;
    }

    @GET
    @Path("/{id}")
    public Response getPractitioner(@Auth Session session, @PathParam("id") String practitionerId) throws StorageException, ResourceCreationException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching practitioner id: %s. Request by: %s", practitionerId, session.getUserEmail()));
        }
        return practitionerAccessService.getPractitioner(session, practitionerId);
    }

    @POST
    public Response createPractitioner(@Auth Session session, @Valid Practitioner practitioner) throws StorageException, ResourceCreationException, DuplicateEntityException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Creating practitioner. Request by: %s", session.getUserEmail()));
        }
        return practitionerAccessService.createPractitioner(session, practitioner);
    }

    @POST
    @Path("/{id}/location")
    public Response addPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId,  @Valid PractitionerLocation practitionerLocation) throws StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Creating practitioner location. PractitionerId: %s, UserEmail: %s.", practitionerId, session.getUserEmail()));
        }
        return practitionerLocationAccessService.createPractitionerLocation(session, practitionerId, practitionerLocation);
    }

    @GET
    @Path("/{id}/location/{locationId}")
    public Response getPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId) throws StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Getting practitioner location. LocationId: %s, PractitionerId: %s, UserEmail: %s.", locationId, practitionerId, session.getUserEmail()));
        }
        return practitionerLocationAccessService.getPractitionerLocation(session, practitionerId, locationId);
    }
}
