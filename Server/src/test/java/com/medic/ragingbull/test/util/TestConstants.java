/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;

import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public interface TestConstants {

    String INPUT_YML = "test-config.yml";

    String HEADER_AUTH = "Auth-Token";
    String TEST_USER_EMAIL_TEMPLATE = "user%d@email.com";
    String TEST_USER_NAME_TEMPLATE = "Test%d User";
    String TEST_USER_PICTURE_URL = "testUserPictureUrl";
    String TEST_USER_PASSWORD = "111111";
    String TEST_USER_PHONE = "9999999999";
    String TEST_USER_INLET_TYPE = "SELF";
    SystemConstants.Sex TEST_USER_SEX = SystemConstants.Sex.MALE;
    DateTime TEST_USER_DOB_MAJOR = new DateTime().minusYears(19);
    DateTime TEST_USER_DOB_MINOR = new DateTime().minusYears(15);


    // URLS
    String BASE_URL = "http://localhost:8080";

    String USER_REGISTRATION_URL = BASE_URL + "/register";
    String USER_RESEND_AUTH_CODE = BASE_URL + "/register/%s";
    String USER_AUTH_CODE_APPROVE = BASE_URL + "/register/%s/approve";

    String USER_GET_DETAILS_SELF = BASE_URL + "/user/me";
}
