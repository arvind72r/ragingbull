/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.services;

import com.google.inject.Inject;
import com.naked.penguin.api.Organization;
import com.naked.penguin.api.OrganizationResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.jdbi.dao.OrganizationDao;
import com.naked.penguin.util.Ids;
import com.naked.penguin.util.Time;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class OrganizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationService.class);
    private OrganizationDao organizationDao;

    @Inject
    public OrganizationService(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public Response create(User user, Organization org) throws ResourceCreationException {
        String organizationId = Ids.generateId(com.naked.penguin.config.Ids.Type.ORG);
        long validity = Time.getMillisAfterXMonths(12);

        int isOrgCreated = organizationDao.create(organizationId, org.getName(), org.getType(), validity);
        if (isOrgCreated == 0) {
            throw  new ResourceCreationException("Error creating org");
        }
        return Response.ok().build();
    }

    public OrganizationResponse get(User user, String orgName) throws ResourceFetchException {
        Organization org = organizationDao.get(orgName);
        if (org == null) {
            throw new ResourceFetchException("Error fetching org");
        }
        return new OrganizationResponse(org);
    }
}
