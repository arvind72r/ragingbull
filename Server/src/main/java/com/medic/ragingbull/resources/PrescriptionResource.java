/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Cart;
import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.PrescriptionAccessService;
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
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@Path("/prescription")
@Produces(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);

    private PrescriptionAccessService prescriptionAccessService;
    @Inject
    public PrescriptionResource(PrescriptionAccessService prescriptionAccessService) {
        this.prescriptionAccessService = prescriptionAccessService;
    }

    @GET
    @Path("/{prescriptionId}")
    public Response getPrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException, ResourceFetchException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Get Prescription: Fetching prescription id: %s. Request by: %s", prescriptionId, session.getUserEmail()));
        }
        Response response = prescriptionAccessService.getPrescription(session, prescriptionId);
        return response;
    }

    @POST
    @Path("/{prescriptionId}/save")
    public Response addDrug(@Auth Session session, @PathParam("prescriptionId") String prescriptionId, @Valid Drug drug) throws StorageException, ResourceFetchException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Add Drug: Adding drug to prescription id: %s. Request by: %s", prescriptionId, session.getUserEmail()));
        }
        Response response = prescriptionAccessService.addDrug(session, prescriptionId, drug);
        return response;
    }

    @DELETE
    @Path("/{prescriptionId}/drug/{drugId}")
    public Response deleteDrug(@Auth Session session, @PathParam("prescriptionId") String prescriptionId, @PathParam("drugId") String drugId) throws StorageException, ResourceFetchException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Deleting Drug: Deleting drug %s, prescription id: %s. Request by: %s", drugId, prescriptionId, session.getUserEmail()));
        }
        Response response = prescriptionAccessService.deleteDrug(session, prescriptionId, drugId);
        return response;
    }

    @POST
    @Path("/{prescriptionId}/order")
    public Response orderPrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId, @Valid Cart cart) throws StorageException, ResourceFetchException, ResourceCreationException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Add Drug: Adding drug to prescription id: %s. Request by: %s", prescriptionId, session.getUserEmail()));
        }
        Response response = prescriptionAccessService.orderPrescription(session, prescriptionId, cart);
        return response;
    }

    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException, ResourceUpdateException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Deleting Prescription: Deleting prescription id: %s. Request by: %s", prescriptionId, session.getUserEmail()));
        }
        Response response = prescriptionAccessService.deletePrescription(session, prescriptionId);
        return response;
    }
}
