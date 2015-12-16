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

    static long phoneNumber = 1111111111l;
    public static User generateUser(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingName(int i) {
        phoneNumber++;
        return new User(null,
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingPassword(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                null,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingPhone(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                null,
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserDOB(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                null);
    }

    public static User generateUserSex(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                null,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingMail(int i) {
        phoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                null,
                String.valueOf(phoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }
}
