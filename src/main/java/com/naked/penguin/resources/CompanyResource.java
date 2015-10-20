/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.Company;
import com.naked.penguin.api.CompanyResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.exception.ResourceUpdateException;
import com.naked.penguin.services.CompanyService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class CompanyResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyResource.class);
    private final CompanyService companyService;

    @Inject
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @POST
    public Response createCompany(@Auth User user, Company company) throws ResourceCreationException {
        return companyService.create(user, company);
    }

    @GET
    @Path("/{companyId}")
    public CompanyResponse getCompany(@Auth User user, @PathParam("companyId") String companyName) throws ResourceFetchException {
        return companyService.getCompany(user, companyName);
    }

    @PUT
    @Path("/{companyId}")
    public CompanyResponse updateCompany(@Auth User user, @PathParam("companyId") String companyName, Company company) throws ResourceUpdateException {
        return companyService.update(user, companyName, company);
    }

}
