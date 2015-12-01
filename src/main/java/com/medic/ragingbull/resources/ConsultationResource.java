/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
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
@Path("/location/{id}/consultation")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationResource.class);

    private ConsultationService consultationService;

    @Inject
    public ConsultationResource(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GET
    public ConsultationResponse getConsultations(@Auth Session session, @PathParam("id") String locationId) throws StorageException {
        ConsultationResponse response = consultationService.getConsultations(session, locationId);
        return response;
    }

    @GET
    @Path("/{consultationId}")
    public ConsultationResponse getConsultation(@Auth Session session, @PathParam("id") String locationId,  @PathParam("consultationId") String consultationId) throws StorageException {
        ConsultationResponse response = consultationService.getConsultation(session, locationId, consultationId);
        return response;
    }

    @POST
    public ConsultationResponse addConsultation(@Auth Session session, @PathParam("locationId") String locationId,  @Valid Consultation consultation) throws StorageException, ResourceCreationException {
        ConsultationResponse response = consultationService.createConsultation(session, locationId, consultation);
        return response;
    }

    @POST
    @Path("/{consultationId}/notes/{type}")
    public Response addNotes(@Auth Session session, @PathParam("id") String locationId,  @PathParam("consultationId") String consultationId, @PathParam("type") SystemConstants.NotesTypes type, String content) throws StorageException, ResourceCreationException {
        Response response = consultationService.createNotes(session, locationId, consultationId, type, content);
        return response;
    }

    @DELETE
    @Path("/{consultationId}/notes/{noteId}")
    public Response deleteNotes(@Auth Session session, @PathParam("id") String locationId,  @PathParam("consultationId") String consultationId, @PathParam("noteId") String noteId) throws StorageException, ResourceCreationException, ResourceUpdateException {
        Response response = consultationService.deleteNote(session, locationId, consultationId, noteId);
        return response;
    }

    @DELETE
    @Path("/{consultationId}")
    public Response deleteConsultation(@Auth Session session, @PathParam("id") String locationId,  @PathParam("consultationId") String consultationId) throws StorageException, ResourceUpdateException {
        Response response = consultationService.deleteConsultation(session, locationId, consultationId);
        return response;
    }
}
