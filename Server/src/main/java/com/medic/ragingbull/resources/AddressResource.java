/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Address;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.AddressAccessService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/user/{id}/address")
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressResource.class);

    private AddressAccessService addressAccessService;

    @Inject
    public AddressResource(AddressAccessService addressAccessService) {
        this.addressAccessService = addressAccessService;
    }

    @GET
    public Response getAddressBook(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = addressAccessService.getAddressBook(session, userId);
        return response;
    }

    @GET
    @Path("/{addressId}")
    public Response getAddress(@Auth Session session, @PathParam("id") String userId, @PathParam("addressId") String addressId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = addressAccessService.getAddress(session, userId, addressId);
        return response;
    }

    @POST
    public Response addAddress(@Auth Session session, @PathParam("id") String userId, @Valid Address address) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = addressAccessService.addAddress(session, userId, address);
        return response;
    }

    @DELETE
    @Path("/{addressId}")
    public Response deleteAddress(@Auth Session session, @PathParam("id") String userId, @PathParam("addressId") String addressId) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = addressAccessService.deleteAddress(session, userId, addressId);
        return response;
    }


}

