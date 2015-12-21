/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.test.util.TestConstants;

/**
 * Created by Vamshi Molleti
 */
public class TestConsultation {
    public static Consultation generateConsultation(String userId, String practitionerId, int i) {
        return new Consultation(userId, practitionerId, String.format(TestConstants.SYMPTOMS, i));
    }

    public static Consultation generateConsultation(String practitionerId, int i) {
        return new Consultation(practitionerId, String.format(TestConstants.SYMPTOMS, i));
    }
}
