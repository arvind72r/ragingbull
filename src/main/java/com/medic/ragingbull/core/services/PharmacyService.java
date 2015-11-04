/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.PharmacyBack;
import com.medic.ragingbull.api.PharmacyResponse;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.jdbi.dao.PharmacistDao;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PharmacyService.class);
    private final PharmacistDao pharmacistDao;

    @Inject
    public PharmacyService(PharmacistDao pharmacistDao) {
        this.pharmacistDao = pharmacistDao;
    }

    public PharmacyResponse createPharmacy(User user, PharmacyBack pharmacyBack, Boolean includeDetails) throws ResourceCreationException {

        try {
            return null;
        } catch(Exception e) {
            LOGGER.error(String.format("Error creating pharmacy name: %s, landmark: %s. Request by: %s. Error: %s", pharmacyBack.getName(), pharmacyBack.getLandmark(), user.getName(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public PharmacyResponse getPharmacy(User user, String pharmacyId) throws ResourceFetchException {
        try {
            PharmacyBack pharmacyBack = pharmacistDao.getById(pharmacyId);
            if (pharmacyBack == null) {
                PharmacyResponse response = new PharmacyResponse();
                response.setStatus(HttpStatus.SC_NOT_FOUND);
                response.setErrorMessage(ErrorMessages.RESOURCE_NOT_FOUND);
                return response;
            } else {
                PharmacyResponse response = new PharmacyResponse(pharmacyBack, false);
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching pharmacy id: %s. Request by: %s. Error: %s", pharmacyId, user.getName(), e));
            throw new ResourceFetchException(e.getMessage());
        }
    }
}
