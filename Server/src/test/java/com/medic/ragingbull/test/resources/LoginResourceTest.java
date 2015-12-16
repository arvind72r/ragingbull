/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.PasswordReset;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginResourceTest extends RagingBullTestApp{

    @Test
    public void testLogin() {
        // Create user
        User user  = TestUser.generateUser(1);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Response loginResponse = ClientUtil.postRequestBasicAuth(user.getEmail(), user.getPassword(), TestConstants.USER_LOGIN, Optional.absent(), null);
        TestUtils.assertValidResponse(loginResponse);
    }

    @Test
    public void testLogout() {
        // Create user
        User user  = TestUser.generateUser(2);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Response logoutResponse = ClientUtil.postRequestBasicAuth(user.getEmail(), user.getPassword(), TestConstants.USER_LOGOUT, Optional.absent(), null);
        TestUtils.assertValidResponse(logoutResponse);
    }

    @Test
    public void testReset() {
        // Create user
        User user  = TestUser.generateUser(3);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        MultivaluedMap queryParams = new MultivaluedHashMap();
        queryParams.add("id", user.getEmail());
        Response resetUserResponse = ClientUtil.postRequestBasicAuth(user.getEmail(), user.getPassword(), TestConstants.USER_RESET, Optional.of(queryParams), null);
        PasswordReset passwordReset = resetUserResponse.readEntity(PasswordReset.class);
        TestUtils.assertValidResponse(resetUserResponse);
        TestUtils.assertEquals(user.getEmail(), passwordReset.getEmail());
    }

    @Test
    public void testResetPassword() {
        // Create user
        User user  = TestUser.generateUser(4);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        MultivaluedMap queryParams = new MultivaluedHashMap();
        queryParams.add("id", user.getEmail());
        Response resetUserResponse = ClientUtil.postRequestBasicAuth(user.getEmail(), user.getPassword(), TestConstants.USER_RESET, Optional.of(queryParams), null);
        PasswordReset passwordReset = resetUserResponse.readEntity(PasswordReset.class);
        TestUtils.assertValidResponse(resetUserResponse);
        TestUtils.assertEquals(user.getEmail(), passwordReset.getEmail());

        // Reset the password with the link
        MultivaluedMap formParams = new MultivaluedHashMap();
        formParams.add("userEmail", user.getEmail());
        formParams.add("password", "222222");
        Response resetPasswordResponse = ClientUtil.postRequestBasicAuthFormData(user.getEmail(), user.getPassword(), TestConstants.USER_RESET_PASSWORD, Optional.of(queryParams), Optional.of(formParams), passwordReset.getId());
        TestUtils.assertValidResponse(resetPasswordResponse);

        // Try to login the user with new password
        Response loginResponse = ClientUtil.postRequestBasicAuth(user.getEmail(), "222222", TestConstants.USER_LOGIN, Optional.absent(), null);
        TestUtils.assertValidResponse(loginResponse);
    }
}
