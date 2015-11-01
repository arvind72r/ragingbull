/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.services.ConsultationService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner/{id}/location/{locationId}/consult")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationResource.class);

    private ConsultationService consultationService;

    @Inject
    public ConsultationResource(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GET
    public ConsultationResponse searchConsultations(@QueryParam("area") Optional<String> area, @QueryParam("type") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{consultId}")
    public ConsultationResponse getConsultations(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, @PathParam("consultId") String consultId) throws StorageException {
        ConsultationResponse response = consultationService.getConsultation(session, practitionerId, locationId, consultId);
        return response;
    }

    @POST
    public ConsultationResponse createConsultation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, Consultation consultation) throws StorageException {
        ConsultationResponse response = consultationService.createConsultation(session, practitionerId, locationId, consultation);
        return response;
    }

    @PUT
    @Path("/{consultId}")
    public ConsultationResponse modifyConsultation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, @PathParam("consultId") String consultId, Consultation consultation) {
        ConsultationResponse response = consultationService.modifyConsultation(session, practitionerId, locationId, consultId, consultation);
        return response;
    }

    @DELETE
    @Path("/{consultId}")
    public ConsultationResponse deactivateConsultation(@Auth Session session, @PathParam("id") String practitionerId, @PathParam("locationId") String locationId, @PathParam("consultId") String consultId) {
        ConsultationResponse response = consultationService.deleteConsultation(session, practitionerId, locationId, consultId);
        return response;
    }
}
