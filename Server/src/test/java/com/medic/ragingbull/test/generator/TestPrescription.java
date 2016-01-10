/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.Dosage;
import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.test.util.TestConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class TestPrescription {
    public static Prescription generatePrescription(String consultationId, String consulteeId, boolean withDrugs) {
        List<Drug> drugs = new ArrayList<>();
        if (withDrugs) {
            Drug drug = new Drug(String.format(TestConstants.NAME, 1), TestConstants.FREQUENCY + 1, Dosage.Schedule.MORNING, TestConstants.DOSE + 1, String.format(TestConstants.UNIT, 1), TestConstants.DAYS + 1);
            Drug drug1 = new Drug(String.format(TestConstants.NAME, 2), TestConstants.FREQUENCY + 1, Dosage.Schedule.MORNING, TestConstants.DOSE + 1, String.format(TestConstants.UNIT, 2), TestConstants.DAYS + 2);
            Drug drug2 = new Drug(String.format(TestConstants.NAME, 3), TestConstants.FREQUENCY + 1, Dosage.Schedule.MORNING, TestConstants.DOSE + 1, String.format(TestConstants.UNIT, 3), TestConstants.DAYS + 3);
            Drug drug3 = new Drug(String.format(TestConstants.NAME, 4), TestConstants.FREQUENCY + 1, Dosage.Schedule.MORNING, TestConstants.DOSE + 1, String.format(TestConstants.UNIT, 4), TestConstants.DAYS + 4);
            Drug drug4 = new Drug(String.format(TestConstants.NAME, 5), TestConstants.FREQUENCY + 1, Dosage.Schedule.MORNING, TestConstants.DOSE + 1, String.format(TestConstants.UNIT, 5), TestConstants.DAYS + 5);
            drugs.add(drug);
            drugs.add(drug1);
            drugs.add(drug2);
            drugs.add(drug3);
            drugs.add(drug4);
        }
        return new Prescription(consultationId, consulteeId, drugs);
    }
}
