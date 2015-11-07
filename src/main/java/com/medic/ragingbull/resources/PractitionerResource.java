/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.core.services.PractitionerService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/practitioner")
@Produces(MediaType.APPLICATION_JSON)
public class PractitionerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerResource.class);

    private PractitionerService practitionerService;

    @Inject
    public PractitionerResource(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    @GET
    @Path("/{id}")
    public PractitionerResponse getPractitioner(@Auth Session session, @PathParam("id") String practitionerId) throws StorageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching practitioner id: %s. Request by: %s", practitionerId, session.getUserEmail()));
        }
        PractitionerResponse practitionerResponse = practitionerService.getPractitioner(session, practitionerId);
        return practitionerResponse;
    }

    @POST
    public PractitionerResponse createPractitioner(@Auth Session session, @Valid Practitioner practitioner) throws StorageException {
        PractitionerResponse practitionerResponse = practitionerService.createPractitioner(session, practitioner);
        return practitionerResponse;
    }
}
