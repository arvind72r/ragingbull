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
    private ConsultationService consultationService;

    @Inject
    public PractitionerLocationResource(ConsultationService consultationService, PractitionerLocationAccessService practitionerLocationAccessService) {
        this.consultationService = consultationService;
        this.practitionerLocationAccessService = practitionerLocationAccessService;
    }

    @GET
    @Path("/{locationId}")
    public Response getPractitionerLocation(@Auth Session session, @PathParam("locationId") String locationId) throws StorageException {
        return practitionerLocationAccessService.getPractitionerLocation(session, locationId);
    }

    @GET
    @Path("/{locationId}/users")
    public Response getUsers(@Auth Session session, @PathParam("locationId") String locationId) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationAccessService.getUsers(session, locationId);
        return response;
    }

    @GET
    @Path("/{locationId}/users/{userId}")
    public Response getUser(@Auth Session session, @PathParam("locationId") String locationId, @PathParam("userId") String userId) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationAccessService.getUser(session, locationId, userId);
        return response;
    }

    @PUT
    @Path("/{locationId}/users")
    public Response addUsers(@Auth Session session, @PathParam("locationId") String locationId, @Valid List<EntityUser> entityUsers) throws StorageException, ResourceCreationException {
        Response response = practitionerLocationAccessService.addUsers(session, locationId, entityUsers);
        return response;
    }

    @POST
    @Path("/{locationId}/consultation")
    public ConsultationResponse addConsultation(@Auth Session session, @PathParam("locationId") String locationId,  @Valid Consultation consultation) throws StorageException, ResourceCreationException {
        ConsultationResponse response = consultationService.createConsultation(session, locationId, consultation);
        return response;
    }

}
