/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner/{practId}/consult")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationResource.class);

    @GET
    public ConsultationResponse searchConsultations(@QueryParam("area") Optional<String> area, @QueryParam("type") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{id}")
    public ConsultationResponse getConsultations(@PathParam("practId") Optional<String> practId, @PathParam("id") Optional<String> id) {
        return null;
    }

    @POST
    public ConsultationResponse createConsultation(Consultation consulation) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public ConsultationResponse modifyConsultation(Consultation consulation) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public ConsultationResponse deactivateConsultation(Consultation consulation) {
        return null;
    }
}
