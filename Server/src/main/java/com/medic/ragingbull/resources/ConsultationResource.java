/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.ConsultationAccessService;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.core.services.PrescriptionService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
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
@Path("/consultation")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationResource.class);

    private ConsultationAccessService consultationAccessService;

    @Inject
    public ConsultationResource(ConsultationAccessService consultationAccessService) {
        this.consultationAccessService = consultationAccessService;
    }

    @GET
    @Path("/{consultationId}")
    public Response getConsultation(@Auth Session session, @PathParam("consultationId") String consultationId) throws StorageException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Consultation: Fetching consultation details id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.getConsultation(session, consultationId);
        return response;
    }

    @POST
    @Path("/{consultationId}/notes/{type}")
    public Response addNotes(@Auth Session session, @PathParam("consultationId") String consultationId, @PathParam("type") SystemConstants.NotesTypes type, String note) throws StorageException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Consultation: Fetching consultation details id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.addNotes(session, consultationId, type, note);
        return response;
    }

    @DELETE
    @Path("/{consultationId}")
    public Response deleteConsultation(@Auth Session session,  @PathParam("consultationId") String consultationId) throws StorageException, ResourceUpdateException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Delete Consultation: Deleting consultation id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.deleteConsultation(session, consultationId);
        return response;
    }

    @POST
    @Path("/{consultationId}/prescription")
    public Response createPrescription(@Auth Session session, @PathParam("consultationId") String consultationId, @Valid Prescription prescription) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Prescription: Creating prescription. Consultation id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.createPrescription(session, consultationId, prescription);
        return response;
    }

    @GET
    @Path("/{consultationId}/prescription")
    public Response getCurrentPrescription(@Auth Session session, @PathParam("consultationId") String consultationId) throws StorageException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Create Prescription: Creating prescription. Consultation id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.getCurrentPrescription(session, consultationId);
        return response;
    }

    @POST
    @Path("/{consultationId}/lock")
    public Response lockConsultation(@Auth Session session,  @PathParam("consultationId") String consultationId) throws StorageException, ResourceCreationException, ResourceUpdateException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Lock Consultation: Locking consultation. Consultation id: %s. Request by: %s", consultationId, session.getUserEmail()));
        }
        Response response = consultationAccessService.lockConsultation(session, consultationId);
        return response;
    }
}
