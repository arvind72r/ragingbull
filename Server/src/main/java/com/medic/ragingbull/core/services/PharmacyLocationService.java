/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Pharmacist;
import com.medic.ragingbull.api.PharmacyLocation;
import com.medic.ragingbull.api.PharmacyLocationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.PharmacistDao;
import com.medic.ragingbull.jdbi.dao.PharmacyLocationDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PharmacyLocationService.class);
    private PharmacistDao pharmacistDao;
    private PharmacyLocationDao pharmacyLocationDao;

    @Inject
    public PharmacyLocationService(PharmacistDao pharmacistDao, PharmacyLocationDao pharmacyLocationDao) {
        this.pharmacistDao = pharmacistDao;
        this.pharmacyLocationDao = pharmacyLocationDao;
    }

    public PharmacyLocationResponse getPharmacyLocation(Session session, String pharmacistId, String pharmacyLocationId) throws StorageException {
        try {
            PharmacyLocation pharmacyLocation = pharmacyLocationDao.getPharmacyLocation(pharmacyLocationId);
            PharmacyLocationResponse response = new PharmacyLocationResponse(pharmacyLocation.getId(), pharmacyLocation.getUserId(), pharmacyLocation.getEntityId(), pharmacyLocation.getName(), pharmacyLocation.getDescription(), pharmacyLocation.getLocation(), pharmacyLocation.getPrimaryContact(), pharmacyLocation.getSecondaryContact(), pharmacyLocation.getAddress1(), pharmacyLocation.getAddress2(), pharmacyLocation.getCity(), pharmacyLocation.getState(), pharmacyLocation.getZip(), pharmacyLocation.getCountry(), pharmacyLocation.getLandmark(), pharmacyLocation.getLongitude(), pharmacyLocation.getLatitude(), pharmacyLocation.getWorkingHours(), pharmacyLocation.getWorkingDays(),pharmacyLocation.getLicense(), pharmacyLocation.getIsVerified(), pharmacyLocation.getIsActive());
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching a pharmacy location with email %s, locationId: %s", session.getUserEmail(), pharmacyLocationId));
            throw new StorageException(String.format("Error fetching pharmacy location with emailId %s", session.getUserEmail()));
        }
    }

    public PharmacyLocationResponse createPharmacyLocation(Session session, String pharmacistId, PharmacyLocation pharmacyLocation) throws StorageException {
        try {
            String userId;
            if (StringUtils.equals(pharmacistId, "me")) {
                Pharmacist pharmacist= pharmacistDao.getByUserId(session.getUserId());
                userId = pharmacist.getUserId();
                pharmacistId = pharmacist.getId();
            } else {
                userId = pharmacistDao.getUserIdById(pharmacistId);
            }

            if (!StringUtils.equals(session.getUserId(), userId)) {
                throw new ResourceCreationException(String.format("Error creating pharmacy location with emailId %s. Logged in user different from practitioner owner", session.getUserEmail()));
            }

            String pharmacyLocationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PHARMACY_LOCATION);

            int pharmacyLocationCreated = pharmacyLocationDao.createPharmacyLocation(pharmacyLocationId, userId, pharmacistId, pharmacyLocation.getName(), pharmacyLocation.getDescription(), pharmacyLocation.getLocation(), pharmacyLocation.getPrimaryContact(), pharmacyLocation.getSecondaryContact(), pharmacyLocation.getAddress1(), pharmacyLocation.getAddress2(), pharmacyLocation.getCity(), pharmacyLocation.getState(), pharmacyLocation.getZip(), pharmacyLocation.getCountry(), pharmacyLocation.getLandmark(), pharmacyLocation.getLongitude(), pharmacyLocation.getLatitude(), pharmacyLocation.getWorkingHours(), pharmacyLocation.getWorkingDays(),pharmacyLocation.getLicense());

            if (pharmacyLocationCreated == 0 ) {
                LOGGER.error(String.format("Error creating a pharmacy location with email %s, locationId: %s", session.getUserEmail(), pharmacistId));
                throw new ResourceCreationException(String.format("Error creating pharmacy location with emailId %s", session.getUserEmail()));
            }

            PharmacyLocationResponse response = new PharmacyLocationResponse(pharmacyLocationId, userId, pharmacyLocation.getEntityId(), pharmacyLocation.getName(), pharmacyLocation.getDescription(), pharmacyLocation.getLocation(), pharmacyLocation.getPrimaryContact(), pharmacyLocation.getSecondaryContact(), pharmacyLocation.getAddress1(), pharmacyLocation.getAddress2(), pharmacyLocation.getCity(), pharmacyLocation.getState(), pharmacyLocation.getZip(), pharmacyLocation.getCountry(), pharmacyLocation.getLandmark(), pharmacyLocation.getLongitude(), pharmacyLocation.getLatitude(), pharmacyLocation.getWorkingHours(), pharmacyLocation.getWorkingDays(),pharmacyLocation.getLicense(), pharmacyLocation.getIsVerified(), pharmacyLocation.getIsActive());
            response.setStatus(HttpStatus.SC_OK);
            return response;

        } catch(Exception e) {
            LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), pharmacistId));
            throw new StorageException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
        }
    }
}
