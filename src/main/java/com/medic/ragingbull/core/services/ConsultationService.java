/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.ConsultationDao;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */

public class ConsultationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationService.class);

    private ConsultationDao consultationDao;

    @Inject
    public ConsultationService(ConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    public ConsultationResponse getConsultation(Session session, String practitionerId, String locationId, String consultId) throws StorageException {

//        try {
//            Consultation consultation = consultationDao.getConsultation(practitionerId, locationId, consultId);
//
//            if (consultation.getUserId() != session.getUserEmail()) {
//                throw new ResourceFetchException(String.format("Error fetching consultation with emailId %s. Logged in user different from consultation owner", session.getUserEmail()));
//            }
//
//            ConsultationResponse response = new ConsultationResponse(consultation.getId(), consultation.getPractitionerId(), consultation.getLocationId(), consultation.getUserId(), consultation.getName(), consultation.getSlot(), consultation.getNotes());
//            response.setStatus(HttpStatus.SC_OK);
//            return response;
//        } catch(Exception e) {
//            LOGGER.error(String.format("Error fetching consultation with email %s and id: %s", session.getUserEmail(), consultId));
//            throw new StorageException(String.format("Error fetching consultation with email %s and id: %s", session.getUserEmail(), consultId));
//        }
        return null;

    }

    public ConsultationResponse createConsultation(Session session, String practitionerId, String locationId, Consultation consultation) throws StorageException {

//        try {
//            String consultationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.CONSULTATION);
//            int consultationCreated = consultationDao.createConsultation(consultationId, practitionerId, locationId, session.getUserId(), consultation.getName(), consultation.getSlot(), consultation.getNotes());
//
//            if (consultationCreated == 0) {
//                LOGGER.error(String.format("Error creating consultation with email %s", session.getUserEmail()));
//                throw new ResourceCreationException(String.format("Error creating consultation with email %s", session.getUserEmail()));
//            }
//
//            ConsultationResponse response = new ConsultationResponse(consultation.getId(), consultation.getPractitionerId(), consultation.getLocationId(), consultation.getUserId(), consultation.getName(), consultation.getSlot(), consultation.getNotes());
//            response.setStatus(HttpStatus.SC_OK);
//        } catch(Exception e) {
//            LOGGER.error(String.format("Error creating consultation with email %s", session.getUserEmail()));
//            throw new StorageException(String.format("Error creating consultation with email %s", session.getUserEmail()));
//        }

        return null;
    }

    public ConsultationResponse modifyConsultation(Session session, String practitionerId, String locationId, String consultId, Consultation consultation) {
        return null;
    }

    public ConsultationResponse deleteConsultation(Session session, String practitionerId, String locationId, String consultId) {
        return null;
    }
}
