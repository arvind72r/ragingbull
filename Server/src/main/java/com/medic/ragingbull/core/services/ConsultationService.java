/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.ConsultationResponse;
import com.medic.ragingbull.api.Notes;
import com.medic.ragingbull.api.Session;
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
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */

public class ConsultationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationService.class);

    private NotificationFactory notificationFactory;
    private ConsultationDao consultationDao;
    private NotesDao notesDao;
    private TransactionalDao transactionalDao;

    @Inject
    public ConsultationService(ConsultationDao consultationDao, NotesDao notesDao, TransactionalDao transactionalDao, NotificationFactory notificationFactory) {
        this.consultationDao = consultationDao;
        this.notesDao = notesDao;
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
                ConsultationResponse response = new ConsultationResponse();
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                return response;
            }
            return new ConsultationResponse(consultation.getId(),consultation.getPractitionerId(), consultation.getLocationId(), consultation.getUserId(), consultation.getSymptoms(), consultation.getDiagnosis(), consultation.getUserNotes(), consultation.getActive(), consultation.getUserName(), consultation.getUserAge(), consultation.getUserPhone(), consultation.getPractitionerName());
        } catch(Exception e) {
            LOGGER.error(String.format("Error getting consultation with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error getting consultation with email %s", session.getUserEmail()));
        }
    }

    public ConsultationResponse getConsultations(Session session, String locationId) {
        try {
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response deleteConsultation(Session session, String consultationId) throws StorageException, ResourceUpdateException {
        try {
            int consultationDeleted = consultationDao.deleteConsultation(consultationId);
            if (consultationDeleted == 0) {
                LOGGER.error(String.format("Error deleting consultation with email %s", session.getUserEmail()));
                throw new ResourceUpdateException(String.format("Error creating consultation with email %s", session.getUserEmail()));
            }

            return Response.ok().build();
        } catch (ResourceUpdateException re) {
            LOGGER.error(String.format("Error deleting consultation with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error deleting consultation with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating consultation with email %s", session.getUserEmail()));
        }
    }

    public Response createNotes(Session session, String consultationId, SystemConstants.NotesTypes type, String content) throws ResourceCreationException, StorageException {

        try {
            String notesId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.NOTES);
            int notesCreated = notesDao.create(notesId, consultationId, type.toString(), content);
            if (notesCreated == 0) {
                LOGGER.error(String.format("Error creating notes notes with email %s", session.getUserEmail()));
                throw new ResourceCreationException(String.format("Error creating consultation with email %s", session.getUserEmail()));
            }

            return Response.ok().entity(new Notes(notesId, consultationId, type, content)).build();
        } catch (ResourceCreationException re) {
            LOGGER.error(String.format("Error creating notes with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error creating notes with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating notes with email %s", session.getUserEmail()));
        }
    }

    public Response deleteNote(Session session, String consultationId, String noteId) throws ResourceUpdateException, StorageException {
        try {
            int noteDeleted = notesDao.deleteNotes(consultationId, noteId);

            if (noteDeleted == 0) {
                LOGGER.error(String.format("Error deleting notes notes with email %s", session.getUserEmail()));
                throw new StorageException(String.format("Error creating notes with email %s", session.getUserEmail()));
            }

            return Response.ok().build();
        }
        catch (StorageException re) {
            LOGGER.error(String.format("Error deleting notes with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error deleting notes with email %s. Exception: %s", session.getUserEmail(), e));
            throw new ResourceUpdateException(String.format("Error creating notes with email %s", session.getUserEmail()));
        }
    }


    public Response lockConsultation(Session session, String consultationId) throws StorageException, ResourceUpdateException {

        try {
            // Lock Consultation, Prescription, Notes
            String userId = transactionalDao.lockConsultation(session.getUserId(), consultationId);

            if (StringUtils.isBlank(userId))
            {
                throw new StorageException("Storage exception");
            }
//            Consultation consultation = consultationDao.getCompleteConsultation(consultationId);
//
//            notificationFactory.notifyUser(userId, Notifiable.NotificationEvent.CONSULTATION_SUBMITTED, );

            return Response.ok().build();
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
}
