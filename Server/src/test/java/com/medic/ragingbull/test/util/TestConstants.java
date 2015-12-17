/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;

import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;

import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
public interface TestConstants {

    String INPUT_YML = "test-config.yml";

    String HEADER_AUTH = "Auth-Token";
    String HEADER_BASIC_AUTH = "Authorization";
    String TEST_USER_EMAIL_TEMPLATE = "user%d@email.com";
    String TEST_USER_NAME_TEMPLATE = "Test%d User";
    String TEST_USER_PICTURE_URL = "testUserPictureUrl";
    String TEST_USER_PASSWORD = "111111";
    //String TEST_USER_PHONE = String.valueOf(new Random(1111111111l).nextInt());
    String TEST_USER_INLET_TYPE = "SELF";
    String TEST_OAUTH_USER_INLET_TYPE = "SELF";
    SystemConstants.Sex TEST_USER_SEX = SystemConstants.Sex.MALE;
    DateTime TEST_USER_DOB_MAJOR = new DateTime().minusYears(19);
    DateTime TEST_USER_DOB_MINOR = new DateTime().minusYears(15);

    // Member
    String TEST_MEMBER_NAME_TEMPLATE = "Test%d Member";
    String TEST_MEMBER_EMAIL_TEMPLATE = "member%d@email.com";
    SystemConstants.Sex TEST_MEMBER_SEX = SystemConstants.Sex.MALE;
    DateTime TEST_MEMBER_DOB_MAJOR = new DateTime().minusYears(19);

    // URLS
    String BASE_URL = "http://localhost:8080";

    String USER_REGISTRATION_URL = BASE_URL + "/register";
    String OAUTH_USER_REGISTRATION_URL = BASE_URL + "/register/oauth";
    String USER_RESEND_AUTH_CODE = BASE_URL + "/register/%s";
    String USER_AUTH_CODE_APPROVE = BASE_URL + "/register/%s/approve";

    String USER_LOGIN = BASE_URL + "/auth/login";
    String USER_LOGOUT = BASE_URL + "/auth/logout";
    String USER_RESET = BASE_URL + "/auth/reset";
    String USER_RESET_PASSWORD = BASE_URL + "/auth/reset/%s";

    String USER_GET_DETAILS = BASE_URL + "/user/%s";
    String USER_ADD_MEMBER = BASE_URL + "/user/%s/member";
    String USER_UPDATE_DETAILS = BASE_URL + "/user/%s/modify/%s";
}
