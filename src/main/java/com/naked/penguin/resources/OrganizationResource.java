/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.Organization;
import com.naked.penguin.api.OrganizationResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.services.OrganizationService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/org")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class OrganizationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationResource.class);
    private OrganizationService organizationService;

    @Inject
    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @POST
    public Response createOrg(@Auth User user, Organization org) throws ResourceCreationException {
        return organizationService.create(user, org);
    }

    @GET
    @Path("/{orgName}")
    public OrganizationResponse getOrg(@Auth User user, @PathParam("orgName") String orgName) throws ResourceFetchException {
        return organizationService.get(user, orgName);
    }
}
