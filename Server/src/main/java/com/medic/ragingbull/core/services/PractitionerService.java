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
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.exception.DuplicateEntityException;
import com.medic.ragingbull.exception.EntityNotFoundException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.EntityUsersDao;
import com.medic.ragingbull.jdbi.dao.PractitionerDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerService.class);

    private final PractitionerDao practitionerDao;
    private final EntityUsersDao entityUsersDao;
    private final TransactionalDao transactionalDao;

    @Inject
    public PractitionerService(EntityUsersDao entityUsersDao, PractitionerDao practitionerDao, TransactionalDao
            transactionalDao) {
        this.entityUsersDao = entityUsersDao;
        this.practitionerDao = practitionerDao;
        this.transactionalDao = transactionalDao;
    }


    public PractitionerResponse createPractitioner(Session session, Practitioner practitioner) throws
            StorageException, ResourceCreationException, DuplicateEntityException {

        try {
            String practitionerId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER);

            // Create practitioner in a transaction.
            boolean practitionerCreated = transactionalDao.createPractitioner(session.getUserId(),
                    UserRoles.Role.NATIVE_PRACTITIONER.getRoleBit(),
                    practitionerId,
                    practitioner.getDescription(),
                    practitioner.getPrimaryContact(),
                    practitioner.getSecondaryContact(),
                    practitioner.getPrimaryId(),
                    practitioner.getSecondaryId(),
                    practitioner.getRegistrationId(),
                    practitioner.getRegistrationAuthority(),
                    practitioner.getLicense());

            if (!practitionerCreated) {
                LOGGER.error(String.format("Error creating a practitioner with email %s", session.getUserEmail()));
                throw new StorageException(String.format("Error creating a practitioner with email %s", session
                        .getUserEmail()));
            }

            PractitionerResponse response = new PractitionerResponse(practitionerId, practitioner.getUserId(), practitioner.getDescription(),
                    practitioner.getPrimaryContact(), practitioner.getSecondaryContact(), practitioner.getPrimaryId()
                    , practitioner.getSecondaryId(), practitioner.getRegistrationId(), practitioner
                    .getRegistrationAuthority(), practitioner.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch (UnableToExecuteStatementException e) {
            throw new DuplicateEntityException("User already registered as practitioner");
        }
        catch (StorageException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(String.format("Error creating a practitioner with email %s. Exception: %s", session
                    .getUserEmail(), e));
            throw new ResourceCreationException("Error creating a practitioner. Please try again");
        }
    }

    public PractitionerResponse getPractitioner(Session session, String practitionerId) throws StorageException,
            ResourceCreationException {
        try {
            Practitioner practitioner;
            if (StringUtils.equals(practitionerId, "me")) {
                practitioner = practitionerDao.getByUserId(session.getUserId());
            } else {
                practitioner = practitionerDao.getById(practitionerId);
            }

            if (practitioner == null) {
                throw new EntityNotFoundException(String.format("Not able to find practitioner by id: %s, By user: %s", practitionerId, session.getUserId()));
            }

            PractitionerResponse response = new PractitionerResponse(practitioner.getId(), practitioner.getUserId(), practitioner
                    .getDescription(), practitioner.getPrimaryContact(), practitioner.getSecondaryContact(),
                    practitioner.getPrimaryId(), practitioner.getSecondaryId(), practitioner.getRegistrationId(),
                    practitioner.getRegistrationAuthority(), practitioner.getLicense());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch (DBIException e) {
            LOGGER.error(String.format("SQLException: Error when fetching practitioner by id: %s. By user: %s. Exception: %s", practitionerId, session.getUserId(), e));
            throw new StorageException("Error fetching practitioner.Please try again");
        } catch (Exception e) {
            LOGGER.error(String.format("Exception: Error fetching a practitioner with email %s. Id: %s. Exception: %s", session
                    .getUserEmail(), practitionerId, e));
            throw new ResourceCreationException("Error fetching practitioner. Please try again");
        }
    }

    public List<Map<String, Object>> getPractitioners() throws StorageException {
        try {
            List<Map<String, Object>> entityUsers = entityUsersDao.getAllByType(SystemConstants.Entities
                    .PRACTITIONER_LOCATION.name());
            return entityUsers;
        } catch (Exception e) {
            LOGGER.error(String.format("Error fetching a all practitioners Exception: %s", e));
            throw new StorageException(String.format("Error fetching a all practitioners Exception: %s", e));
        }
    }
}
