/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.api.PractitionerLocationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.PractitionerDao;
import com.medic.ragingbull.jdbi.dao.PractitionerLocationDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationService.class);

    private PractitionerDao practitionerDao;
    private PractitionerLocationDao practitionerLocationDao;

    @Inject
    public PractitionerLocationService(PractitionerDao practitionerDao, PractitionerLocationDao practitionerLocationDao) {
        this.practitionerDao = practitionerDao;
        this.practitionerLocationDao = practitionerLocationDao;
    }

    public PractitionerLocationResponse getPractitioner(Session session, String practitionerId, String locationId) throws StorageException {
        try {
            String userId = practitionerDao.getUserIdPractitionerById(practitionerId);
            if (!StringUtils.equals(session.getUserId(), userId)) {
                throw new ResourceFetchException(String.format("Error fetching practitioner location with emailId %s. Logged in user different from practitioner owner", session.getUserEmail()));
            }

            PractitionerLocation practitionerLocation = practitionerLocationDao.getPractitionerLocation(locationId);
            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocation.getId(), practitionerLocation.getPractitionerId(), practitionerLocation.getName(), practitionerLocation.getContactName(), practitionerLocation.getDescription(), practitionerLocation.getDiscipline(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
            throw new StorageException(String.format("Error fetching practitioner location with emailId %s", session.getUserEmail()));
        }
    }

    public PractitionerLocationResponse createPractitionerLocation(Session session, String practitionerId, PractitionerLocation practitionerLocation) throws StorageException {
        try {
            String userId = practitionerDao.getUserIdPractitionerById(practitionerId);
            if (!StringUtils.equals(session.getUserId(), userId)) {
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s. Logged in user different from practitioner owner", session.getUserEmail()));
            }

            String practitionerLocationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER_LOCATION);
            Boolean verified = false;
            Boolean active = true;

            int practitionerLocationCreated = practitionerLocationDao.createPractitionerLocation(practitionerLocationId, practitionerId, practitionerLocation.getName(), practitionerLocation.getContactName(), practitionerLocation.getDescription(), practitionerLocation.getDiscipline(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getLicenseDoc(), practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());

            if (practitionerLocationCreated == 0 ) {
                LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
            }

            practitionerLocation.setId(practitionerLocationId);
            practitionerLocation.setIsVerified(verified);
            practitionerLocation.setIsActive(active);

            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocation.getId(), practitionerLocation.getPractitionerId(), practitionerLocation.getName(), practitionerLocation.getContactName(), practitionerLocation.getDescription(), practitionerLocation.getDiscipline(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            response.setStatus(HttpStatus.SC_OK);
            return response;

        } catch(Exception e) {
            LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
            throw new StorageException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
        }
    }
}
