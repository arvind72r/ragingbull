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
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.ConsultationDao;
import com.medic.ragingbull.jdbi.dao.NotesDao;
import org.apache.http.HttpStatus;
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

    private ConsultationDao consultationDao;

    private NotesDao notesDao;

    @Inject
    public ConsultationService(ConsultationDao consultationDao, NotesDao notesDao) {
        this.consultationDao = consultationDao;
        this.notesDao = notesDao;
    }

    public ConsultationResponse createConsultation(Session session, String locationId, Consultation consultation) throws StorageException, ResourceCreationException {

        try {
            String consultationId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.CONSULTATION);

            List<Notes> consultationNotes = new ArrayList<>();

            for (String symptom : consultation.getSymptoms()) {
                String noteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.NOTES);
                Notes symptomNote = new Notes(noteId, consultationId, SystemConstants.NotesTypes.SYMPTOMS, symptom);
                consultationNotes.add(symptomNote);
            }

            for (String symptom : consultation.getDiagnosis()) {
                String noteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.NOTES);
                Notes symptomNote = new Notes(noteId, consultationId, SystemConstants.NotesTypes.DIAGNOSIS, symptom);
                consultationNotes.add(symptomNote);
            }

            for (String symptom : consultation.getUserNotes()) {
                String noteId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.NOTES);
                Notes symptomNote = new Notes(noteId, consultationId, SystemConstants.NotesTypes.USER, symptom);
                consultationNotes.add(symptomNote);
            }

            String creatorId = consultation.getCreatorId() != null ? consultation.getCreatorId() : session.getUserId();

            int consultationCreated = consultationDao.createConsultation(consultationId, locationId, consultation.getPractitionerId(), consultation.getUserId(), creatorId);

            if (consultationCreated == 0) {
                LOGGER.error(String.format("Error creating consultation with email %s", session.getUserEmail()));
                throw new ResourceCreationException(String.format("Error creating consultation with email %s", session.getUserEmail()));
            }

            int notesCreated = 1;
            for (Notes notes : consultationNotes) {
                notesCreated = notesDao.create(notes.getId(), notes.getEntityId(), notes.getEntityType().name(), notes.getContent());
            }
            //int notesCreated = notesDao.createAll(consultationNotes);

            if (notesCreated == 0) {
                LOGGER.error(String.format("Error creating consultation notes with email %s", session.getUserEmail()));
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

    public ConsultationResponse getConsultation(Session session, String locationId, String consultationId) throws StorageException {
        try {

            Consultation consultation = consultationDao.getConsultationDetails(consultationId);

            List<Notes> notes = notesDao.getNotes(consultationId);

            for (Notes note : notes) {
                if (note.getEntityType() == SystemConstants.NotesTypes.SYMPTOMS) {
                    consultation.getSymptoms().add(note.getContent());
                } else if (note.getEntityType() == SystemConstants.NotesTypes.DIAGNOSIS) {
                    consultation.getDiagnosis().add(note.getContent());
                } else if (note.getEntityType() == SystemConstants.NotesTypes.USER) {
                    consultation.getUserNotes().add(note.getContent());
                }
            }

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

    public Response deleteConsultation(Session session, String locationId, String consultationId) throws StorageException, ResourceUpdateException {
        try {
            int consultationDeleted = consultationDao.deleteConsultation(consultationId, locationId);
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

    public Response createNotes(Session session, String locationId, String consultationId, SystemConstants.NotesTypes type, String content) throws ResourceCreationException, StorageException {

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

    public Response deleteNote(Session session, String locationId, String consultationId, String noteId) throws ResourceUpdateException, StorageException {
        try {
            int noteDeleted = notesDao.deleteNotes(consultationId, noteId);

            if (noteDeleted == 0) {
                LOGGER.error(String.format("Error deleting notes notes with email %s", session.getUserEmail()));
                throw new ResourceUpdateException(String.format("Error creating notes with email %s", session.getUserEmail()));
            }

            return Response.ok().build();
        }
        catch (ResourceUpdateException re) {
            LOGGER.error(String.format("Error deleting notes with email %s. Exception: %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error deleting notes with email %s. Exception: %s", session.getUserEmail(), e));
            throw new StorageException(String.format("Error creating notes with email %s", session.getUserEmail()));
        }
    }


}
