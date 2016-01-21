/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.medic.ragingbull.api.Session;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/user/{id}/address")
public class AddressResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressResource.class);

    @GET
    @Path("/{addressId}")
    public Response getAddressBook(@Auth Session session, @PathParam("id") String userId, @PathParam("addressId") String addressId) {
        return null;
    }
}

