/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.exception.ResourceUpdateException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.DrugsDao;
import com.medic.ragingbull.jdbi.dao.PrescriptionDao;
import com.medic.ragingbull.util.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class PrescriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionService.class);
    private PrescriptionDao prescriptionDao;
    private DrugsDao drugsDao;

    @Inject
    public PrescriptionService(PrescriptionDao prescriptionDao, DrugsDao drugsDao) {
        this.prescriptionDao = prescriptionDao;
        this.drugsDao = drugsDao;
    }

    public PrescriptionResponse getPrescription(Session session, String prescriptionId) throws ResourceFetchException {

        try {
            Prescription prescription = prescriptionDao.getPrescription(prescriptionId);
            List<Drug> drugsList = drugsDao.getByPrescriptionId(prescriptionId);

            return new PrescriptionResponse(prescription.getId(), prescription.getConsultationId(), prescription.getPractitionerId(), prescription.getUserId(), drugsList, prescription.getActive());
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching prescription: %s. for user %s. Exception %s", prescriptionId, session.getUserEmail(), e));
            throw new ResourceFetchException(e.getMessage());
        }
    }

    public PrescriptionResponse createPrescription(Session session, String consultationId, Prescription prescription) throws ResourceCreationException, StorageException {
        try {

            String prescriptionId = Ids.generateId(com.medic.ragingbull.core.constants.Ids.Type.PRESCRIPTION);

            for (int i = 0; i < prescription.getDrugs().size(); i++) {
                String drugId = Ids.generateId(com.medic.ragingbull.core.constants.Ids.Type.DRUG);

                prescription.getDrugs().get(i).setId(drugId);
                prescription.getDrugs().get(i).setConsultationId(consultationId);
                prescription.getDrugs().get(i).setPractitionerId(session.getUserId());
                prescription.getDrugs().get(i).setPrescriptionId(prescriptionId);
                prescription.getDrugs().get(i).setUserId(prescription.getUserId());
            }

            int prescriptionCreated = prescriptionDao.createPrescription(prescriptionId, consultationId, session.getUserId(), prescription.getUserId());

            if (prescriptionCreated == 0) {
                LOGGER.error(String.format("Error creating prescription for consultation: %s. by user: %s.", consultationId, session.getUserEmail()));
                throw new StorageException("Error creating prescription. Please try again");
            }

            int[] drugsCreated = drugsDao.createAll(prescription.getDrugs());

            if (drugsCreated.length < prescription.getDrugs().size()) {
                LOGGER.error(String.format("Error creating prescription for consultation: %s. by user: %s.", consultationId, session.getUserEmail()));
                throw new StorageException("Error creating prescription. Please try again");
            }
            return new PrescriptionResponse(prescriptionId, consultationId, session.getUserId(), prescription.getUserId(), prescription.getDrugs(), prescription.getActive());
        } catch (StorageException e) {
            LOGGER.error(String.format("Error creating prescription for consultation: %s. by user: %s.", consultationId, session.getUserEmail()));
            throw e;
        } catch (Exception e) {
            LOGGER.error(String.format("Error creating prescription for consultation: %s. for user %s. Exception %s", consultationId, session.getUserEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public Response deletePrescription(Session session, String prescriptionId) throws StorageException, ResourceUpdateException {
        try {
            int prescriptionDeleted = prescriptionDao.deletePrescription(prescriptionId);

            if (prescriptionDeleted == 0) {
                LOGGER.error(String.format("Error deleting prescription Id: %s. by user: %s.", prescriptionId, session.getUserEmail()));
                throw new StorageException("Error deleting prescription. Please try again");
            }
            return Response.ok().build();
        } catch (StorageException e) {
            LOGGER.error(String.format("Error deleting prescription Id: %s. by user: %s.", prescriptionId, session.getUserEmail()));
            throw e;
        } catch(Exception e) {
            LOGGER.error(String.format("Error deleting prescription for Id: %s. for user %s. Exception %s", prescriptionId, session.getUserEmail(), e));
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    public Response addDrug(Session session, String prescriptionId, Drug drug) throws ResourceCreationException, StorageException {
        try {

            Prescription prescription = prescriptionDao.getPrescription(prescriptionId);
            String drugId = Ids.generateId(com.medic.ragingbull.core.constants.Ids.Type.DRUG);

            int drugAdded = drugsDao.add(drugId, prescription.getConsultationId(), prescription.getUserId(), prescription.getPractitionerId(), prescriptionId, drug.getName(), drug.getManufacturer(), drug.getQuantity(), drug.getFrequency(), drug.getAllergies());
            if (drugAdded == 0) {
                LOGGER.error(String.format("Error adding drug for prescription: %s. by user: %s.", prescriptionId, session.getUserEmail()));
                throw new StorageException("Error adding drug for prescription. Please try again");
            }
            return Response.ok().build();
        } catch (StorageException e) {
            LOGGER.error(String.format("Error adding drug for prescription: %s. by user: %s.", prescriptionId, session.getUserEmail()));
            throw e;
        } catch(Exception e) {
            LOGGER.error(String.format("Error adding drug for prescription: %s. by user: %s. Exception %s", prescriptionId, session.getUserEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public PrescriptionResponse deleteDrug(Session session, String prescriptionId, String drugId) {
        return null;
    }
}
