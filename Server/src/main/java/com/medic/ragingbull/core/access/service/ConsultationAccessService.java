/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.ConsultationService;
import com.medic.ragingbull.core.services.PrescriptionService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class ConsultationAccessService {
    private ConsultationService consultationService;
    private PrescriptionService prescriptionService;

    @Inject
    public ConsultationAccessService(ConsultationService consultationService, PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
        this.consultationService = consultationService;
    }

    public Response getConsultation(Session session, String consultationId) throws StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_READ.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_READ.getBitValue()) {
            ConsultationResponse response = consultationService.getConsultation(session, consultationId);
            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response deleteConsultation(Session session, String consultationId) throws ResourceUpdateException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_DELETE.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_DELETE.getBitValue()) {
            boolean success = consultationService.deleteConsultation(session, consultationId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response createPrescription(Session session, String consultationId, Prescription prescription) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD.getBitValue()) {
            PrescriptionResponse response = prescriptionService.createPrescription(session, consultationId, prescription);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response lockConsultation(Session session, String consultationId) throws ResourceUpdateException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY.getBitValue()) {
            boolean success = consultationService.lockConsultation(session, consultationId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getCurrentPrescription(Session session, String consultationId) {
        PrescriptionResponse response = prescriptionService.getCurrentPrescription(session, consultationId);
        return Response.ok().entity(response).build();
    }

    public Response addNotes(Session session, String consultationId, SystemConstants.NotesTypes type, String note) {
        if (type == SystemConstants.NotesTypes.DIAGNOSIS) {
            // The requester needs to have Consultation MODIFY permission
            if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY.getBitValue()) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        if ((session.getRole() & UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_READ.getBitValue()) == UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_READ.getBitValue()) {
            boolean success = consultationService.updateNotes(session, consultationId, type, note);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();

            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
