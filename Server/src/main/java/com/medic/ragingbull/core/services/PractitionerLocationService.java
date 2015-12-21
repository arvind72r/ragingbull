/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.UserRoleGenerator;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PractitionerLocationService.class);

    private final UserRoleGenerator userRoleGenerator;

    private final UsersDao usersDao;
    private final PractitionerDao practitionerDao;
    private final PractitionerLocationDao practitionerLocationDao;
    private final EntityUsersDao entityUsersDao;
    private final TransactionalDao transactionalDao;

    @Inject
    public PractitionerLocationService(UsersDao usersDao, EntityUsersDao entityUsersDao, PractitionerLocationDao practitionerLocationDao, PractitionerDao practitionerDao, UserRoleGenerator userRoleGenerator, TransactionalDao transactionalDao) {
        this.usersDao = usersDao;
        this.userRoleGenerator = userRoleGenerator;
        this.entityUsersDao = entityUsersDao;
        this.practitionerLocationDao = practitionerLocationDao;
        this.practitionerDao = practitionerDao;
        this.transactionalDao = transactionalDao;
    }

    public PractitionerLocationResponse getPractitionerLocation(Session session, String locationId) throws StorageException {
        try {
            PractitionerLocation practitionerLocation = practitionerLocationDao.getPractitionerLocation(locationId);
            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocation.getId(), practitionerLocation.getUserId(), practitionerLocation.getEntityId(), practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(), practitionerLocation.getLicense(), practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            return response;
        } catch (Exception e) {
            LOGGER.error(String.format("Error fetching a practitioner location with email %s, locationId: %s", session.getUserEmail(), locationId));
            throw new StorageException(String.format("Error fetching practitioner location with emailId %s", session.getUserEmail()));
        }
    }

    public PractitionerLocationResponse createPractitionerLocation(Session session, String practitionerId, PractitionerLocation practitionerLocation) throws StorageException {
        try {
            String userId;
            if (StringUtils.equals(practitionerId, "me")) {
                Practitioner practitioner = practitionerDao.getByUserId(session.getUserId());
                userId = practitioner.getUserId();
                practitionerId = practitioner.getId();
            } else {
                userId = practitionerDao.getUserIdPractitionerById(practitionerId);
            }

            if (!StringUtils.equals(session.getUserId(), userId)) {
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s. Logged in user different from practitioner owner", session.getUserEmail()));
            }

            String practitionerLocationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PRACTITIONER_LOCATION);
            boolean practitionerLocationCreated = transactionalDao.
            createPractitionerLocation(userId, UserRoles.Role.NATIVE_PRACTITIONER_LOCATION.getRoleBit(), practitionerLocationId, practitionerId,
                    practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(),
                    practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(),
                    practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation
                            .getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(),
                    practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(),
                    practitionerLocation.getWorkingDays(), practitionerLocation.getLicense());

            if (!practitionerLocationCreated) {
                LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
            }

            PractitionerLocationResponse response = new PractitionerLocationResponse(practitionerLocationId, userId, practitionerId, practitionerLocation.getName(), practitionerLocation.getDescription(), practitionerLocation.getSpeciality().toString(), practitionerLocation.getLocation(), practitionerLocation.getPrimaryContact(), practitionerLocation.getSecondaryContact(), practitionerLocation.getAddress1(), practitionerLocation.getAddress2(), practitionerLocation.getCity(), practitionerLocation.getState(), practitionerLocation.getZip(), practitionerLocation.getCountry(), practitionerLocation.getLandmark(), practitionerLocation.getLongitude(), practitionerLocation.getLatitude(), practitionerLocation.getWorkingHours(), practitionerLocation.getWorkingDays(), practitionerLocation.getLicense(), practitionerLocation.getIsVerified(), practitionerLocation.getIsActive());
            response.setStatus(HttpStatus.SC_OK);
            return response;

        } catch (Exception e) {
            LOGGER.error(String.format("Error creating a practitioner location with email %s, locationId: %s", session.getUserEmail(), practitionerId));
            throw new StorageException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
        }
    }

    public Boolean addUsers(Session session, String locationId, List<EntityUser> entityUsers) throws ResourceCreationException {
        for (EntityUser entityUser : entityUsers) {

            User user = usersDao.getRoleById(entityUser.getUserId());

            if (user == null) {
                // Throw error
            }

            String entityId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.ENTITY_USER);
            List<UserRoles.Permissions> permissionsList = new ArrayList<>();
            permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD);
            permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY);
            permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_DELETE);

            Long userRole = userRoleGenerator.generateRole(user.getRole(), permissionsList);
            entityUser.setId(entityId);
            entityUser.setCreatorId(session.getUserId());
            entityUser.setEntityId(locationId);
            entityUser.setEntity(SystemConstants.Entities.PRACTITIONER_LOCATION);

            boolean entityUserCreated = transactionalDao.addEntityUsers(entityUser.getUserId(), userRole, entityId, session.getUserId(), locationId, entityUser.getEntity().name());

            if (!entityUserCreated) {
                LOGGER.error(String.format("Error creating a entity user for  location with email %s, locationId: %s", session.getUserEmail(), locationId));
                throw new ResourceCreationException(String.format("Error creating practitioner location with emailId %s", session.getUserEmail()));
            }
        }
        return true;
    }

    public ImmutableList<EntityUser> getUsers(Session session, String locationId) {
        ImmutableList<EntityUser> entityAdmins = entityUsersDao.getAll(locationId);
        return entityAdmins;
    }

    public EntityUser getUsers(Session session, String locationId, String userId) {
        EntityUser entityAdmin = entityUsersDao.getUser(locationId, userId);
        return entityAdmin;
    }
}
