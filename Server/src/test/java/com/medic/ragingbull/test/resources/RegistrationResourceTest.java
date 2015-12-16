/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.exception.RagingBullBaseException;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationResourceTest extends RagingBullTestApp{

    @Test
    public void test1UserRegistration() throws RagingBullBaseException {

        // Create user
        User user  = TestUser.generateUser(1);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);
    }

    @Test
    public void test2DuplicateUserRegistration() {
        // Create user
        User user  = TestUser.generateUser(2);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Try to create the same user again
        Response conflictResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        TestUtils.assertConflict(conflictResponse);
    }

    @Test
    public void test3ResendInviteCode() {
        // Create user
        User user  = TestUser.generateUser(3);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Check if able to resend InviteAuthCode
        Response resendInviteCode = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_RESEND_AUTH_CODE, Optional.absent(), session.getUserId());

        TestUtils.assertValidResponse(resendInviteCode);
    }

    @Test
    public void test4ResendInviteCodeMe() {
        // Create user
        User user  = TestUser.generateUser(4);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Check if able to resend InviteAuthCode
        Response resendInviteCode = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_RESEND_AUTH_CODE, Optional.absent(), "me");

        TestUtils.assertValidResponse(resendInviteCode);
    }

    @Test
    public void test5ApproveUser() throws RagingBullBaseException{
        // Create user
        User user  = TestUser.generateUser(5);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        String authCode = userService.resendInviteAuthCode(session, session.getUserId());

        Response userApproveResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_AUTH_CODE_APPROVE, Optional.absent(), Optional.absent(), authCode);
        TestUtils.assertValidResponse(userApproveResponse);

        Response userApproveResponseInvalidAuth = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_AUTH_CODE_APPROVE, Optional.absent(), Optional.absent(), "0000");
        TestUtils.assertConflict(userApproveResponseInvalidAuth);
    }

    @Test
    public void test6UserRegistrationMissingName() {
        // Create user with missing name
        User missingName = TestUser.generateUserMissingName(6);
        Response missingNameResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingName);
        TestUtils.assertUnProcessableEntity(missingNameResponse);
    }

    @Test
    public void test7UserRegistrationMissingPassword() {
        // Create user with missing password
        User missingPassword = TestUser.generateUserMissingPassword(7);
        Response missingPasswordResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingPassword);
        TestUtils.assertUnProcessableEntity(missingPasswordResponse);
    }

    @Test
    public void test8UserRegistrationMissingEmail() {
        // Create user with missing email,
        User missingEmail = TestUser.generateUserMissingMail(8);
        Response missingEmailResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingEmail);
        TestUtils.assertValidResponse(missingEmailResponse);
    }

    @Test
    public void test9UserRegistrationMissingPhone() {
        // Create user with missing phone
        User missingPhone = TestUser.generateUserMissingPhone(9);
        Response missingPhoneResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingPhone);
        TestUtils.assertUnProcessableEntity(missingPhoneResponse);
    }

    @Test
    public void test99UserRegistrationMissingDOB() {
        // Create user with missing DOB
        User missingDOB = TestUser.generateUserDOB(10);
        Response missingDOBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingDOB);
        TestUtils.assertUnProcessableEntity(missingDOBResponse);
    }

    @Test
    public void test999UserRegistrationMissingSex() {
        // Create user with missing sex
        User missingSex = TestUser.generateUserSex(11);
        Response missingSexResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), missingSex);
        TestUtils.assertUnProcessableEntity(missingSexResponse);
    }
}
