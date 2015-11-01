/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.api.PractitionerLocationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.services.PractitionerLocationService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner/{id}/location")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerLocationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationResource.class);

    private PractitionerLocationService practitionerLocationService;

    @Inject
    public PractitionerLocationResource(PractitionerLocationService practitionerLocationService) {
        this.practitionerLocationService = practitionerLocationService;
    }

    @GET
    public PractitionerLocationResponse searchLocations() {
        return null;
    }

    @GET
    @Path("/{locationId}")
    public PractitionerLocationResponse getPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId) throws StorageException {
        PractitionerLocationResponse response = practitionerLocationService.getPractitioner(session, practitionerId, locationId);
        return response;
    }

    @POST
    public PractitionerLocationResponse addPractitionerLocation(@Auth Session session, @PathParam("id") String practitionerId,  @Valid PractitionerLocation practitionerLocation) throws StorageException {
        PractitionerLocationResponse response = practitionerLocationService.createPractitionerLocation(session, practitionerId, practitionerLocation);
        return response;
    }

    @PUT
    @Path("/{locationId}")
    public PractitionerLocationResponse updatePractitionerLocation(@PathParam("id") String practitionerId, @PathParam("locationId") String locationId, PractitionerLocation practitionerLocation) {
        return null;
    }

    @DELETE
    @Path("/{locationId}")
    public PractitionerLocationResponse deletePractitionerLocation(@PathParam("id") String practitionerId, @PathParam("locationId") String locationId){
        return null;
    }
}
