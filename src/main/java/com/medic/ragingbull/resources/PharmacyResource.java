/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Pharmacy;
import com.medic.ragingbull.api.PharmacyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/pharmacy")
@Produces(MediaType.APPLICATION_JSON)
public class PharmacyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PharmacyResource.class);

    @GET
    public PharmacyResponse searchPharmacist(@QueryParam("area") Optional<String> area, @QueryParam("type") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{id}")
    public PharmacyResponse getPharmacist(@PathParam("id") Optional<String> id) {
        return null;
    }

    @POST
    public PharmacyResponse createPharmacist(Pharmacy pharmacy) {
        return null;
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
