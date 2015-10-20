/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.services;

import com.google.inject.Inject;
import com.naked.penguin.api.Company;
import com.naked.penguin.api.CompanyResponse;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.exception.ResourceUpdateException;
import com.naked.penguin.jdbi.dao.CompanyDao;
import com.naked.penguin.util.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyDao companyDao;

    @Inject
    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public Response create(User user, Company company) throws ResourceCreationException {
        String companyId = Ids.generateId(com.naked.penguin.config.Ids.Type.COMPANY);
        int isCompanyCreated = companyDao.create(companyId, company.getName(), company.getDisplayName(), company.getIntro());
        if (isCompanyCreated == 0) {
            throw new ResourceCreationException("Error creating company");
        }
        return Response.ok().build();
    }

    public CompanyResponse getCompany(User user, String companyName) throws ResourceFetchException {
        Company company = companyDao.get(companyName);
        if (company == null) {
            throw new ResourceFetchException("Error fetching company");
        }
        return new CompanyResponse(company);
    }

    public CompanyResponse update(User user, String companyName, Company company) throws ResourceUpdateException {
        int isCompanyUpdated = companyDao.update(companyName, company.getDisplayName(), company.getIntro());
        if (isCompanyUpdated == 0) {
            throw new ResourceUpdateException("Error updating company");
        }
        return new CompanyResponse(company);
    }
}
