/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.core.services.PractitionerLocationService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationAccessService {
    private PractitionerLocationService practitionerLocationService;
    private ConsultationService consultationService;

    @Inject
    public PractitionerLocationAccessService(PractitionerLocationService practitionerLocationService, ConsultationService consultationService) {
        this.practitionerLocationService = practitionerLocationService;
        this.consultationService = consultationService;
    }
    
    public Response getAllLocations(Session session) throws StorageException{
    	if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            List<Map<String, Object>> responseList = practitionerLocationService.getAllLocations();
            return Response.ok().entity(responseList).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build(); 
    	
    }

    public Response createPractitionerLocation(Session session, String practitionerId, PractitionerLocation practitionerLocation) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_ADD.getBitValue()) {
            PractitionerLocationResponse response = practitionerLocationService.createPractitionerLocation(session, practitionerId, practitionerLocation);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getPractitionerLocation(Session session, String locationId) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_READ.getBitValue()) != UserRoles.Permissions.PRACTITIONER_LOCATION_READ.getBitValue()) {
            PractitionerLocationResponse response = practitionerLocationService.getPractitionerLocation(session, locationId);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getUsers(Session session, String locationId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            ImmutableList<EntityUser> users = practitionerLocationService.getUsers(session, locationId);
            return Response.ok().entity(users).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getUser(Session session, String locationId, String userId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            EntityUser user = practitionerLocationService.getUsers(session, locationId, userId);
            return Response.ok().entity(user).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response addUsers(Session session, String locationId, List<EntityUser> entityUsers) throws ResourceCreationException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_USER_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_USER_ADD.getBitValue()) {
            boolean success = practitionerLocationService.addUsers(session, locationId, entityUsers);
            if (success) {
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }


    public Response createConsultation(Session session, String locationId, Consultation consultation) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD.getBitValue()) {
            ConsultationResponse response = consultationService.createConsultation(session, locationId, consultation);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }
}
