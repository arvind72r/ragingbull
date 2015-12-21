/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.generator;

import com.medic.ragingbull.api.EntityUser;
import com.medic.ragingbull.api.Member;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.test.util.TestConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class TestUser {

    static long userPhoneNumber =   1111111111l;
    static long memberPhoneNumber = 2222222222l;
    public static User generateUser(int i) {
        userPhoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static Member generateMember(int i) {
        return new Member(String.format(TestConstants.TEST_MEMBER_NAME_TEMPLATE, i),
                String.format(TestConstants.TEST_MEMBER_EMAIL_TEMPLATE, i),
                String.valueOf(memberPhoneNumber++),
                TestConstants.TEST_MEMBER_SEX,
                TestConstants.TEST_MEMBER_DOB_MAJOR);
    }

    public static User generateUserMissingName(int i) {
        userPhoneNumber++;
        return new User(null,
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingPassword(int i) {
        userPhoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                null,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingPhone(int i) {
        userPhoneNumber++;
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
        userPhoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                null);
    }

    public static User generateUserSex(int i) {
        userPhoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                null,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateUserMissingMail(int i) {
        userPhoneNumber++;
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                null,
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }

    public static User generateOAuthUser(int i) {
        return new User(String.format(TestConstants.TEST_USER_NAME_TEMPLATE, i),
                TestConstants.TEST_USER_PASSWORD,
                String.format(TestConstants.TEST_USER_EMAIL_TEMPLATE, i),
                String.valueOf(userPhoneNumber),
                TestConstants.TEST_OAUTH_USER_INLET_TYPE,
                TestConstants.TEST_USER_PICTURE_URL,
                TestConstants.TEST_USER_SEX,
                TestConstants.TEST_USER_DOB_MAJOR);
    }


    public static EntityUser generateLocationEntityUser(String userId) {
        // Test premissions for a entity user at location. These can be practitioner or normal acting as front desk
        List<UserRoles.Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD);
        permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_READ);
        permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY);
        permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_DELETE);
        permissionsList.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD);
        return new EntityUser(userId, permissionsList);
    }
}
