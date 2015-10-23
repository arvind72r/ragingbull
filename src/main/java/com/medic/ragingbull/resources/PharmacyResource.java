/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Pharmacy;
import com.medic.ragingbull.api.PharmacyResponse;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.services.PharmacyService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/pharmacy")
@Produces(MediaType.APPLICATION_JSON)
public class PharmacyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PharmacyResource.class);
    private final PharmacyService pharmacyService;

    @Inject
    public PharmacyResource(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }


    @GET
    public PharmacyResponse searchPharmacist(@QueryParam("contactName") Optional<String> area, @QueryParam("contactNumber") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{id}")
    public PharmacyResponse getPharmacist(@Auth User user, @PathParam("id") Optional<String> id) throws ResourceFetchException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching pharmacist id: %s. Request by: %s", id.get(), user.getName()));
        }

        PharmacyResponse response = pharmacyService.getPharmacy(user, id.get());
        return response;
    }

    @POST
    public PharmacyResponse createPharmacist(@Auth User user, @QueryParam("")  Optional<Boolean> includeDetails, Pharmacy pharmacy) throws ResourceCreationException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Creating new pharmacist name: %s, landmark: %s. Request by: %s", pharmacy.getName(), pharmacy.getLandmark(), user.getName()));
        }

        PharmacyResponse response = pharmacyService.createPharmacy(user, pharmacy, includeDetails.get());
        return response;
    }

    @PUT
    public PharmacyResponse modifyPharmacist(Pharmacy pharmacy) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public PharmacyResponse deactivatePharmacist(Pharmacy pharmacy) {
        return null;
    }
}
