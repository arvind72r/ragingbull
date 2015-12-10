/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.PrescriptionService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/prescription")
@Produces(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);

    private PrescriptionService prescriptionService;

    @Inject
    public PrescriptionResource(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GET
    @Path("/{prescriptionId}")
    public PrescriptionResponse getPrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException {
        PrescriptionResponse response = prescriptionService.getPrescription(session, prescriptionId);
        return response;
    }



    @DELETE
    @Path("/{prescriptionId}")
    public PrescriptionResponse deletePrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException {
        PrescriptionResponse response = prescriptionService.getPrescription(session, prescriptionId);
        return response;
    }

}
