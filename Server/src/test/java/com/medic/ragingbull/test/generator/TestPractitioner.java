/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.core.constants.LocationSpeciality;
import com.medic.ragingbull.test.util.TestConstants;

/**
 * Created by Vamshi Molleti
 */
public class TestPractitioner {

    public static Practitioner generatePractitioner(int i) {
        return new Practitioner(String.format(TestConstants.TEST_PRACTITIONER_DESCRIPTION, i),
                String.valueOf(TestConstants.TEST_PRACTITIONER_PHONE),
                String.valueOf(TestConstants.TEST_PRACTITIONER_PHONE),
                String.format(TestConstants.TEST_PRACTITIONER_PRIMARYID, i),
                String.format(TestConstants.TEST_PRACTITIONER_SECONDARYID, i),
                String.format(TestConstants.TEST_PRACTITIONER_REGISTRATIONID, i),
                String.format(TestConstants.TEST_PRACTITIONER_ISSUING_AUTHORITY, i),
                String.format(TestConstants.TEST_PRACTITIONER_LICENSE, i));
    }
}
