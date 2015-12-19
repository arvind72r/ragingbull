/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.api.PractitionerLocationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.PractitionerLocationService;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationAccessService {
    private PractitionerLocationService practitionerLocationService;

    @Inject
    public PractitionerLocationAccessService(PractitionerLocationService practitionerLocationService) {
        this.practitionerLocationService = practitionerLocationService;
    }

    public Response createPractitionerLocation(Session session, String practitionerId, PractitionerLocation practitionerLocation) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_ADD.getBitValue()) != UserRoles.Permissions.PRACTITIONER_LOCATION_ADD.getBitValue()) {
            PractitionerLocationResponse response = practitionerLocationService.createPractitionerLocation(session, practitionerId, practitionerLocation);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getPractitionerLocation(Session session, String practitionerId, String locationId) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_READ.getBitValue()) != UserRoles.Permissions.PRACTITIONER_LOCATION_READ.getBitValue()) {
            PractitionerLocationResponse response = practitionerLocationService.getPractitionerLocation(session, practitionerId, locationId);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }
}
