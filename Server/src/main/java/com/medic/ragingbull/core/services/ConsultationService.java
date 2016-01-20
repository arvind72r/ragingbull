/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.core.notification.NotificationFactory;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.ConsultationDao;
import com.medic.ragingbull.jdbi.dao.NotesDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */

public class ConsultationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationService.class);

    private NotificationFactory notificationFactory;
    private ConsultationDao consultationDao;
    private TransactionalDao transactionalDao;

    @Inject
    public ConsultationService(ConsultationDao consultationDao, NotesDao notesDao, TransactionalDao transactionalDao, NotificationFactory notificationFactory) {
        this.consultationDao = consultationDao;
        this.transactionalDao = transactionalDao;
        this.notificationFactory = notificationFactory;
    }

    public ConsultationResponse createConsultation(Session session, String locationId, Consultation consultation) throws StorageException, ResourceCreationException {

        try {
            String consultationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.CONSULTATION);

            String creatorId = consultation.getCreatorId() != null ? consultation.getCreatorId() : session.getUserId();

            String userId = consultation.getUserId() != null ? consultation.getUserId() : session.getUserId();

            int consultationCreated = consultationDao.createConsultation(consultationId, locationId, consultation.getPractitionerId(), userId, creatorId, consultation.getSymptoms(), consultation.getDiagnosis(), consultation.getUserNotes());

            if (consultationCreated == 0) {
                LOGGER.error(String.format("Error creating consultation with email %s", session.getUserEmail()));
                throw new ResourceCreationException(String.format("Error creating consultation with email %s", session.getUserEmail()));
            }

            ConsultationResponse response = new ConsultationResponse(consultationId, consultation.getPractitionerId(), locationId, consultation.getUserId(), consultation.getSymptoms(), consultation.getDiagnosis(), consultation.getUserNotes(), consultation.getActive());
            response.setStatus(HttpStatus.SC_OK);
            return response;
        } catch (ResourceCreationException re) {
            LOGGER.error(String.format("Error creating consultation with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error creating consultation with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating consultation with email %s", session.getUserEmail()));
        }
    }

    public ConsultationResponse getConsultation(Session session, String consultationId) throws StorageException {
        try {

            Consultation consultation = consultationDao.getConsultationDetails(consultationId);

            if (consultation == null) {
                return null;
            }
            return new ConsultationResponse(consultation.getId(),consultation.getPractitionerId(), consultation.getLocationId(), consultation.getUserId(), consultation.getSymptoms(), consultation.getDiagnosis(), consultation.getUserNotes(), consultation.getActive(), consultation.getUserName(), consultation.getUserAge(), consultation.getUserPhone(), consultation.getPractitionerName(), null);
        } catch(Exception e) {
            LOGGER.error(String.format("Error getting consultation with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error getting consultation with email %s", session.getUserEmail()));
        }
    }

    public boolean deleteConsultation(Session session, String consultationId) throws StorageException, ResourceUpdateException {
        try {
            int consultationDeleted = consultationDao.deleteConsultation(consultationId);
            if (consultationDeleted == 0) {
                LOGGER.error(String.format("Error deleting consultation with email %s", session.getUserEmail()));
                throw new ResourceUpdateException(String.format("Error creating consultation with email %s", session.getUserEmail()));
            }

            return true;
        } catch (ResourceUpdateException re) {
            LOGGER.error(String.format("Error deleting consultation with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error deleting consultation with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating consultation with email %s", session.getUserEmail()));
        }
    }

    public ConsultationResponse lockConsultation(Session session, String consultationId) throws StorageException, ResourceUpdateException {

        try {
            // Lock Consultation, Prescription, Notes
            boolean success = transactionalDao.lockConsultation(consultationId);

            if (!success)
            {
                throw new StorageException("Storage exception");
            }

            Consultation consultation = transactionalDao.getCompleteConsultation(consultationId);
            notificationFactory.notifyUser(consultation.getUserId(), Notifiable.NotificationEvent.CONSULTATION_SUBMITTED, consultation);
            return new ConsultationResponse(consultation.getId(),consultation.getPractitionerId(), consultation.getLocationId(), consultation.getUserId(), consultation.getSymptoms(), consultation.getDiagnosis(), consultation.getUserNotes(), consultation.getActive(), consultation.getUserName(), consultation.getUserAge(), consultation.getUserPhone(), consultation.getPractitionerName(), consultation.getPrescription());
        }
        catch (StorageException e) {
            LOGGER.error(String.format("Error locking consultation with consultationId: %s, email %s. Exception: %s", consultationId, session.getUserEmail(), e));
            throw e;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error locking consultation with consultationId: %s, email %s. Exception: %s", consultationId, session.getUserEmail(), e));
            throw new ResourceUpdateException(String.format("Error locking consultation"));
        }
    }


    public boolean updateNotes(Session session, String consultationId, SystemConstants.NotesTypes type, String note) {
        if (type == SystemConstants.NotesTypes.DIAGNOSIS) {
            // Update diagnosis
            consultationDao.updateDiagnosis(consultationId, note);
        } else if (type == SystemConstants.NotesTypes.SYMPTOMS) {
            // Update symptoms
            consultationDao.updateSymptom(consultationId, note);
        }
        return true;
    }

    public CartResponse getConsultationCart(Session session, String consultationId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Fetching the current cart or fresh cart for the consultation: %s", consultationId));
        }

        // Check if there is already a cart for the entity "Consultation". If not create a cart and return it back

        return null;
    }
}
