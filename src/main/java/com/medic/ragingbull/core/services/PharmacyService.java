/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Pharmacist;
import com.medic.ragingbull.api.PharmacistResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.PharmacistDao;
import org.apache.commons.lang3.StringUtils;
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

    public PharmacistResponse getPharmacist(Session session, String id) throws StorageException {
        Pharmacist pharmacist;
        try {
            if (StringUtils.equals(id, "me")) {
                pharmacist = pharmacistDao.getByUserId(session.getUserId());
            } else {
                pharmacist = pharmacistDao.getById(id);
            }

            if (pharmacist == null) {
                LOGGER.error(String.format("Error fetching a pharmacist with email %s. Id: %s", session.getUserEmail(), id));
                throw new ResourceFetchException(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
            }

            PharmacistResponse response = new PharmacistResponse(pharmacist.getId(), pharmacist.getDescription(), pharmacist.getPrimaryContact(), pharmacist.getSecondaryContact(), pharmacist.getPrimaryId(), pharmacist.getSecondaryId(), pharmacist.getRegistrationId(), pharmacist.getRegistrationAuthority(), pharmacist.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;

        } catch (Exception e) {
            LOGGER.error(String.format("Error fetching a pharmacist with email %s. Id: %s. Exception: %s", session.getUserEmail(), id, e));
            throw new StorageException(String.format("Error fetching a pharmacist with email %s. Exception: %s", session.getUserEmail(), e));
        }
    }

    public PharmacistResponse createPharmacist(Session session, Pharmacist pharmacist) throws StorageException {

        try {
            String pharmacistId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PHARMACY);
            int practitionerCreated = pharmacistDao.createPharmacist(pharmacistId, session.getUserId(), pharmacist.getDescription(), pharmacist.getPrimaryContact(), pharmacist.getSecondaryContact(), pharmacist.getPrimaryId(), pharmacist.getSecondaryId(), pharmacist.getRegistrationId(), pharmacist.getRegistrationAuthority(), pharmacist.getLicense());

            if (practitionerCreated == 0) {
                LOGGER.error(String.format("Error creating a pharmacist with email %s", session.getUserEmail()));
                throw new ResourceCreationException(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
            }

            PharmacistResponse response = new PharmacistResponse(pharmacistId, pharmacist.getDescription(), pharmacist.getPrimaryContact(), pharmacist.getSecondaryContact(), pharmacist.getPrimaryId(), pharmacist.getSecondaryId(), pharmacist.getRegistrationId(), pharmacist.getRegistrationAuthority(), pharmacist.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch (Exception e) {
            LOGGER.error(String.format("Error creating a pharmacist with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating a practitioner with email %s. Exception: %s", session.getUserEmail(), e));
        }
    }
}
