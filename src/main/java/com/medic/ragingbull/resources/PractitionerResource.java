/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerResource.class);

    @GET
    public PractitionerResponse searchPractitioner(@QueryParam("area") Optional<String> area, @QueryParam("type") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{id}")
    public PractitionerResponse getPractitioner(@PathParam("id") Optional<String> id) {
        return null;
    }

    @POST
    public PractitionerResponse createPractitioner(Practitioner practitioner) {
        return null;
    }

    @PUT
    public PractitionerResponse modifyPractitioner(Practitioner practitioner) {
        return null;
    }

    @DELETE
    public PractitionerResponse deactivatePractitioner(Practitioner practitioner) {
        return null;
    }

}
