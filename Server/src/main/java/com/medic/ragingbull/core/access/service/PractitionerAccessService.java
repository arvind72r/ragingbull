/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.PractitionerService;
import com.medic.ragingbull.exception.DuplicateEntityException;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerAccessService {

    private PractitionerService practitionerService;

    @Inject
    public PractitionerAccessService(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    public Response getPractitioners(Session session) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            List<Map<String, Object>> responseList = practitionerService.getPractitioners();
            return Response.ok().entity(responseList).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response createPractitioner(Session session, Practitioner practitioner) throws StorageException, ResourceCreationException, DuplicateEntityException {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            PractitionerResponse response = practitionerService.createPractitioner(session, practitioner);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getPractitioner(Session session, String practitionerId) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_READ.getBitValue()) != UserRoles.Permissions.PRACTITIONER_READ.getBitValue()) {
            // Check if the request is spoofed
            PractitionerResponse response = practitionerService.getPractitioner(session, practitionerId);
            if (!StringUtils.equals(session.getUserId(), response.getUserId())) {
                // Check if the user has permission to access others resources
                if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_READ_OTHER.getBitValue()) != UserRoles.Permissions.PRACTITIONER_READ_OTHER.getBitValue()) {
                    return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
                }
            }
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }
}
