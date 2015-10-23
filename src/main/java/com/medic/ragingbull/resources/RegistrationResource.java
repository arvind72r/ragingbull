/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.medic.ragingbull.api.RegistrationResponse;
import com.medic.ragingbull.api.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);

    @GET
    @Path("/{id}")
    public RegistrationResponse registrationStatus(@PathParam("id") Option<String> registrationId) {
        return null;
    }

    @POST
    public RegistrationResponse registerUser(User user) {
        return null;
    }

}
