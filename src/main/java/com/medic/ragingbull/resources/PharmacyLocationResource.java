/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.PharmacyLocation;
import com.medic.ragingbull.api.PharmacyLocationResponse;
import com.medic.ragingbull.api.PractitionerLocationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.PharmacyLocationService;
import com.medic.ragingbull.core.services.PharmacyService;
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
@Path("/pharmacist/{id}/location")
@Produces(MediaType.APPLICATION_JSON)
public class PharmacyLocationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PharmacistResource.class);
    private final PharmacyLocationService pharmacyLocationService;

    @Inject
    public PharmacyLocationResource(PharmacyLocationService pharmacyLocationService) {
        this.pharmacyLocationService = pharmacyLocationService;
    }

    @GET
    @Path("/{locationId}")
    public PharmacyLocationResponse getPractitionerLocation(@Auth Session session, @PathParam("id") String pharmacistId, @PathParam("locationId") String locationId) throws StorageException {
        PharmacyLocationResponse response = pharmacyLocationService.getPharmacyLocation(session, pharmacistId, locationId);
        return response;
    }

    @POST
    public PharmacyLocationResponse addPractitionerLocation(@Auth Session session, @PathParam("id") String pharmacistId,  @Valid PharmacyLocation pharmacyLocation) throws StorageException {
        PharmacyLocationResponse response = pharmacyLocationService.createPharmacyLocation(session, pharmacistId, pharmacyLocation);
        return response;
    }
}
