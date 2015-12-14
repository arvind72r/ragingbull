/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.User;
import com.medic.ragingbull.test.util.TestConstants;

/**
 * Created by Vamshi Molleti
 */
public class TestUser {

    private static int userCount = 0;

    public static User generateUser() {
        User testUser = new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, userCount),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, userCount),
                TestConstants.TEST_USER_PHONE,
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR
                );

        return testUser;
    }
}
