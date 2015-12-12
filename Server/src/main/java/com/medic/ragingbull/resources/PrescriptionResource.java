/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
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
    public PrescriptionResponse getPrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException, ResourceFetchException {
        PrescriptionResponse response = prescriptionService.getPrescription(session, prescriptionId);
        return response;
    }

    @POST
    @Path("/{prescriptionId}/drug")
    public Response addDrug(@Auth Session session, @PathParam("prescriptionId") String prescriptionId, @Valid Drug drug) throws StorageException, ResourceFetchException, ResourceCreationException {
        Response response = prescriptionService.addDrug(session, prescriptionId, drug);
        return response;
    }

    @DELETE
    @Path("/{prescriptionId}/drug/{drugId}")
    public Response deleteDrug(@Auth Session session, @PathParam("prescriptionId") String prescriptionId, @PathParam("drugId") String drugId) throws StorageException, ResourceFetchException, ResourceCreationException {
        Response response = prescriptionService.deleteDrug(session, prescriptionId, drugId);
        return response;
    }

    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@Auth Session session, @PathParam("prescriptionId") String prescriptionId) throws StorageException, ResourceUpdateException {
        Response response = prescriptionService.deletePrescription(session, prescriptionId);
        return response;
    }
}
