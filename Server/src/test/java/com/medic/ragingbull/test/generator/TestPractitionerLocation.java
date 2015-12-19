/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.test.util.TestConstants;

/**
 * Created by Vamshi Molleti
 */
public class TestPractitionerLocation {
    public static PractitionerLocation createPractitionerLocation(int i) {
        return new PractitionerLocation(String.format(TestConstants.TEST_PRACTITIONER_LOCATION_NAME, i),
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_DESCRIPTION, i),
                TestConstants.TEST_PRACTITIONER_LOCATION_SPECIALITY,
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_LOCATION, i),
                String.valueOf(TestConstants.TEST_PRACTITIONER_LOCATION_PRIMARY_CONTACT),
                String.valueOf(TestConstants.TEST_PRACTITIONER_LOCATION_SECONDARY_CONTACT),
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_ADDRESS1, i),
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_ADDRESS2, i),
                TestConstants.TEST_PRACTITIONER_LOCATION_CITY,
                TestConstants.TEST_PRACTITIONER_LOCATION_STATE,
                TestConstants.TEST_PRACTITIONER_LOCATION_ZIP,
                TestConstants.TEST_PRACTITIONER_LOCATION_COUNTRY,
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_LANDMARK, i),
                TestConstants.TEST_PRACTITIONER_LOCATION_LONGITUDE,
                TestConstants.TEST_PRACTITIONER_LOCATION_LATITUDE,
                TestConstants.TEST_PRACTITIONER_LOCATION_WORKING_HOURS,
                TestConstants.TEST_PRACTITIONER_LOCATION_WORKING_DAYS,
                String.format(TestConstants.TEST_PRACTITIONER_LOCATION_LICENSE, i));
    }
}
