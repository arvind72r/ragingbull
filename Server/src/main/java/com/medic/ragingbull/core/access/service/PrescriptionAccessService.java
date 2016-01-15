/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.PrescriptionService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class PrescriptionAccessService {
    private PrescriptionService prescriptionService;

    @Inject
    public PrescriptionAccessService(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    public Response getPrescription(Session session, String prescriptionId) throws ResourceFetchException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            PrescriptionResponse response = prescriptionService.getPrescription(session, prescriptionId);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response addDrug(Session session, String prescriptionId, Drug drug) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) {
            boolean success = prescriptionService.addDrug(session, prescriptionId, drug);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response deleteDrug(Session session, String prescriptionId, String drugId) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) {
            boolean success = prescriptionService.deleteDrug(session, prescriptionId, drugId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response deletePrescription(Session session, String prescriptionId) throws ResourceUpdateException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) {
            boolean success = prescriptionService.deletePrescription(session, prescriptionId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response orderPrescription(Session session, String prescriptionId, Cart cart) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            OrderResponse orderResponse = prescriptionService.orderPrescription(session, prescriptionId, cart);
            return Response.ok().entity(orderResponse).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
