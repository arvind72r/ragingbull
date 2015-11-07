/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.core.services.PharmacyService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/pharmacist")
@Produces(MediaType.APPLICATION_JSON)
public class PharmacistResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PharmacistResource.class);
    private final PharmacyService pharmacyService;

    @Inject
    public PharmacistResource(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @GET
    @Path("/{id}")
    public PharmacistResponse getPharmacist(@Auth Session session, @PathParam("id") String id) throws ResourceFetchException, StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching pharmacist id: %s. Request by: %s", id, session.getUserEmail()));
        }
        PharmacistResponse response = pharmacyService.getPharmacist(session, id);
        return response;
    }

    @POST
    public PharmacistResponse createPharmacist(@Auth Session session, Pharmacist pharmacist) throws ResourceCreationException, StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Creating new pharmacist user with email: %s", session.getUserEmail()));
        }

        PharmacistResponse response = pharmacyService.createPharmacist(session, pharmacist);
        return response;
    }
}
