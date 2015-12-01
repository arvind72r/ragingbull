/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.PractitionerDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerService.class);

    private PractitionerDao practitionerDao;

    @Inject
    public PractitionerService(PractitionerDao practitionerDao) {
        this.practitionerDao = practitionerDao;
    }

    public PractitionerResponse createPractitioner(Session session, Practitioner practitioner) throws StorageException {

        try {
            String practitionerId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER);

            int practitionerCreated = practitionerDao.createPractitioner(practitionerId, session.getUserId(), practitioner.getDescription(), practitioner.getPrimaryContact(), practitioner.getSecondaryContact(), practitioner.getPrimaryId(), practitioner.getSecondaryId(), practitioner.getRegistrationId(), practitioner.getRegistrationAuthority(), practitioner.getLicense());

            if (practitionerCreated == 0) {
                LOGGER.error(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
                throw new ResourceCreationException(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
            }

            PractitionerResponse response = new PractitionerResponse(practitionerId, practitioner.getDescription(), practitioner.getPrimaryContact(), practitioner.getSecondaryContact(), practitioner.getPrimaryId(), practitioner.getSecondaryId(), practitioner.getRegistrationId(), practitioner.getRegistrationAuthority(), practitioner.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error creating a practitioner with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating a practitioner with email %s. Exception: %s", session.getUserEmail(), e));
        }

    }

    public PractitionerResponse getPractitioner(Session session, String practitionerId) throws StorageException {

        try {
            Practitioner practitioner;
            if (StringUtils.equals(practitionerId, "me")) {
                practitioner = practitionerDao.getByUserId(session.getUserId());
            } else {
                practitioner = practitionerDao.getById(practitionerId);
            }

            if (practitioner == null) {
                LOGGER.error(String.format("Error fetching a practitioner with email %s. Id: %s", session.getUserEmail(), practitionerId));
                throw new ResourceFetchException(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
            }

            PractitionerResponse response = new PractitionerResponse(practitioner.getId(), practitioner.getDescription(), practitioner.getPrimaryContact(), practitioner.getSecondaryContact(), practitioner.getPrimaryId(), practitioner.getSecondaryId(), practitioner.getRegistrationId(), practitioner.getRegistrationAuthority(), practitioner.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching a practitioner with email %s. Id: %s. Exception: %s", session.getUserEmail(), practitionerId, e));
            throw new StorageException(String.format("Error fetching a practitioner with email %s. Exception: %s", session.getUserEmail(), e));
        }
    }
}
