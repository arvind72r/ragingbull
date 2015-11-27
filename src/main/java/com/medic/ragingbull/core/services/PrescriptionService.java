/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.jdbi.dao.DrugsDao;
import com.medic.ragingbull.jdbi.dao.PrescriptionDao;
import com.medic.ragingbull.util.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public PrescriptionResponse getPrescription(Session session, String practitionerId, String locationId, String consultId, String prescriptionId) {

        Prescription prescription = prescriptionDao.getPrescription(prescriptionId);


        return null;
    }

    public PrescriptionResponse createPrescription(Session session, String consultationId, Prescription prescription) {
        try {

            String prescriptionId = Ids.generateId(com.medic.ragingbull.core.constants.Ids.Type.PRESCRIPTION);

            for (int i = 0; i < prescription.getDrugs().size(); i++) {

                String drugId = Ids.generateId(com.medic.ragingbull.core.constants.Ids.Type.DRUG);

                prescription.getDrugs().get(i).setId(drugId);
                prescription.getDrugs().get(i).setConsultationId(consultationId);
                prescription.getDrugs().get(i).setPractitionerId(session.getUserId());
                prescription.getDrugs().get(i).setUserId(prescription.getUserId());
            }
            int prescriptionCreated = prescriptionDao.createPrescription(prescriptionId, consultationId, session.getUserId(), prescription.getUserId());

            if (prescriptionCreated == 0) {

            }

            int drugsCreated = drugsDao.createAll(prescription.getDrugs());

            if (drugsCreated == 0) {

            }

            return new PrescriptionResponse();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
