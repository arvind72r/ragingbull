/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.permissions.Privileges;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.EntityAdminDao;
import com.medic.ragingbull.jdbi.dao.PractitionerDao;
import com.medic.ragingbull.jdbi.dao.PractitionerLocationDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationService.class);

    private PractitionerDao practitionerDao;
    private PractitionerLocationDao practitionerLocationDao;
    private EntityAdminDao entityAdminDao;

    @Inject
    public PractitionerLocationService(EntityAdminDao entityAdminDao, PractitionerLocationDao practitionerLocationDao, PractitionerDao practitionerDao) {
        this.entityAdminDao = entityAdminDao;
        this.practitionerLocationDao = practitionerLocationDao;
        this.practitionerDao = practitionerDao;
    }

    public PractitionerLocationResponse getPractitioner(Session session, String practitionerId, String practitionerLocationId) throws StorageException {
        try {
            PractitionerLocation practitionerLocation = practitionerLocationDao.getPractitionerLocation(practitionerLocationId);
            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocation.getId(), practitionerLocation.getUserId(), practitionerLocation.getEntityId(), practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getLicense(), practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerLocationId));
            throw new StorageException(String.format("Error fetching practitioner location with emailId %s", session.getUserEmail()));
        }
    }

    public PractitionerLocationResponse createPractitionerLocation(Session session, String practitionerId, PractitionerLocation practitionerLocation) throws StorageException {
        try {
            String userId;
            if (StringUtils.equals(practitionerId, "me")) {
                Practitioner practitioner= practitionerDao.getByUserId(session.getUserId());
                userId = practitioner.getUserId();
                practitionerId = practitioner.getId();
            } else {
                userId = practitionerDao.getUserIdPractitionerById(practitionerId);
            }

            if (!StringUtils.equals(session.getUserId(), userId)) {
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s. Logged in user different from practitioner owner", session.getUserEmail()));
            }

            String practitionerLocationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER_LOCATION);

            int practitionerLocationCreated = practitionerLocationDao.createPractitionerLocation(practitionerLocationId, userId, practitionerId, practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getLicense());

            if (practitionerLocationCreated == 0 ) {
                LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
            }

            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocationId, userId, practitionerLocation.getEntityId(), practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(),practitionerLocation.getLicense(), practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            response.setStatus(HttpStatus.SC_OK);
            return response;

        } catch(Exception e) {
            LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
            throw new StorageException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
        }
    }

    public Response addUsers(Session session, String practitionerId, String locationId, List<Map<String, String>> users) throws ResourceCreationException {
        for(Map<String, String> user : users) {
            String adminId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.ENTITY_ADMIN);
            Integer userRole = 0;
            String userId = user.get("id");
            String privileges = user.get("roles");

            StringTokenizer tokenizer = new StringTokenizer(privileges, ",");
            while (tokenizer.hasMoreTokens()) {
                userRole = userRole | Privileges.valueOf(tokenizer.nextToken()).getBitValue();
            }
            int entityCreated = entityAdminDao.create(adminId, userId, session.getUserId(), locationId, userRole, SystemConstants.Entities.PHARMACY_LOCATION.toString());

            if (entityCreated == 0) {
                LOGGER.error(String.format("Error creating an admin users by user with email %s, locationId: %s", session.getUserEmail(), practitionerId));
                throw new ResourceCreationException(String.format("Error creating an admin users by user with email %s", session.getUserEmail()));
            }

            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response getUsers(Session session, String practitionerId, String locationId) {
        ImmutableList<EntityAdmin> entityAdmins = entityAdminDao.getAll(locationId);
        return Response.ok().entity(entityAdmins).build();
    }

    public Response getUser(Session session, String practitionerId, String locationId, String userId) {
        EntityAdmin entityAdmin = entityAdminDao.getUser(locationId, userId);
        return Response.ok().entity(entityAdmin).build();
    }
}
