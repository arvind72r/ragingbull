/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.api.PrescriptionResponse;
import com.medic.ragingbull.api.Session;
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

    @Inject
    public PrescriptionService(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public PrescriptionResponse getPrescription(Session session, String practitionerId, String locationId, String consultId, String prescriptionId) {

        Prescription prescription = prescriptionDao.getPrescription(prescriptionId);


        return null;
    }

    public PrescriptionResponse createPrescription(Session session, String practitionerId, String locationId, String consultId, Prescription prescription) {
        try {

            String prescriptionId = Ids.generateId(com.medic.ragingbull.config.Ids.Type.PRESCRIPTION);
            prescriptionDao.createPrescription(prescriptionId, practitionerId, locationId, consultId, prescription.getDrugs());

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
