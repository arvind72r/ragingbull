/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Member;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.h2.util.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest extends RagingBullTestApp {

    @Test
    public void testGetUserDetails() {

        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Response userResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), session.getUserId());
        User userDetails = userResponse.readEntity(User.class);
        TestUtils.assertEquals(session, userDetails);
    }

    @Test
    public void testGetUserDetailsSelf() {

        User user = TestUser.generateUser(1);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Response userResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), "me");
        User userDetails = userResponse.readEntity(User.class);
        TestUtils.assertEquals(session, userDetails);
    }

    @Test
    public void testUpdateUserDetails() {

        User user = TestUser.generateUser(2);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        String updatedName = "Batman";
        String updatedEmail = "batman@gotham.com";
        String updatedPassword = "222222";

        // Update Name
        Map<String, String> updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedName);
        Response userNameResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, session.getUserId(), "name");
        TestUtils.assertValidResponse(userNameResponse);

        Response updateNameDetailsResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), session.getUserId());
        User nameUpdatedUser = updateNameDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(updatedName, nameUpdatedUser.getName());

        // Update email
        updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedEmail);
        Response userEmailResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, session.getUserId(), "email");
        TestUtils.assertValidResponse(userEmailResponse);

        Response updateEmailDetailsResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), session.getUserId());
        User emailUpdatedUser = updateEmailDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(updatedEmail, emailUpdatedUser.getEmail());

        // Update password
        Map<String, String> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("password", user.getPassword());
        updatePasswordMap.put("password1", updatedPassword);
        updatePasswordMap.put("password2", updatedPassword);

        Response userPasswordResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updatePasswordMap, session.getUserId(), "password");
        TestUtils.assertValidResponse(userPasswordResponse);

        Response passwordUpdatedUser = ClientUtil.getRequestWithBasicAuth(updatedEmail, updatedPassword, TestConstants.USER_GET_DETAILS, Optional.absent(), session.getUserId());
        User updatedUser = passwordUpdatedUser.readEntity(User.class);
        TestUtils.assertEquals(session.getUserId(), updatedUser.getId());
    }

    @Test
    public void testUpdateUserDetailsSelf() {

        User user = TestUser.generateUser(3);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        String updatedName = "Batman";
        String updatedEmail = "batman@gotham.com";
        String updatedPassword = "222222";

        // Update Name
        Map<String, String> updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedName);
        Response userNameResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, "me", "name");
        TestUtils.assertValidResponse(userNameResponse);

        Response updateNameDetailsResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), "me");
        User nameUpdatedUser = updateNameDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(updatedName, nameUpdatedUser.getName());

        // Update email
        updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedEmail);
        Response userEmailResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, "me", "email");
        TestUtils.assertValidResponse(userEmailResponse);

        Response updateEmailDetailsResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), "me");
        User emailUpdatedUser = updateEmailDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(updatedEmail, emailUpdatedUser.getEmail());

        // Update password
        Map<String, String> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("password", user.getPassword());
        updatePasswordMap.put("password1", updatedPassword);
        updatePasswordMap.put("password2", updatedPassword);

        Response userPasswordResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updatePasswordMap, "me", "password");
        TestUtils.assertValidResponse(userPasswordResponse);

        Response passwordUpdatedUser = ClientUtil.getRequestWithBasicAuth(updatedEmail, updatedPassword, TestConstants.USER_GET_DETAILS, Optional.absent(), "me");
        User updatedUser = passwordUpdatedUser.readEntity(User.class);
        TestUtils.assertEquals(session.getUserId(), updatedUser.getId());
    }

    @Test
    public void testAddMember() {

        User user = TestUser.generateUser(5);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Add member
        Member member0 = TestUser.generateMember(0);
        Response member0Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member0, "me");
        TestUtils.assertValidResponse(member0Response);

        Member member1 = TestUser.generateMember(1);
        Response member1Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member1, "me");
        TestUtils.assertValidResponse(member1Response);

        Member member2 = TestUser.generateMember(2);
        Response member2Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member2, "me");
        TestUtils.assertValidResponse(member2Response);
    }

    @Test
    public void testGetMembers() {

        User user = TestUser.generateUser(6);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Add member
        Member member0 = TestUser.generateMember(3);
        Response member0Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member0, "me");
        TestUtils.assertValidResponse(member0Response);

        Member member1 = TestUser.generateMember(4);
        Response member1Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member1, "me");
        TestUtils.assertValidResponse(member1Response);

        Member member2 = TestUser.generateMember(5);
        Response member2Response = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), member2, "me");
        TestUtils.assertValidResponse(member2Response);

        Response getAllMembersResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_ADD_MEMBER, Optional.absent(), "me");
        List<User> members = getAllMembersResponse.readEntity(new GenericType<List<User>>(){});

        for (User member : members) {
            if ( !(StringUtils.equals(member.getPhone(), member0.getPhone()) | StringUtils.equals(member.getPhone(), member1.getPhone()) | StringUtils.equals(member.getPhone(), member2.getPhone())) ) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testGetUserDetailsDifferentId() {

        User userA = TestUser.generateUser(7);
        Response userAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session userASession = userAResponse.readEntity(Session.class);
        TestUtils.validateSession(userASession);

        User userB = TestUser.generateUser(8);
        Response userBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session userBSession = userBResponse.readEntity(Session.class);
        TestUtils.validateSession(userBSession);


        // UserA trying to fetch userB details
        Response userResponse = ClientUtil.getRequestWithAuth(userASession.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), userBSession.getUserId());
        TestUtils.assertForbidden(userResponse);

    }

    @Test
    public void testUpdateUserDetailsDifferentId() {

        User userA = TestUser.generateUser(9);
        Response userAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session userASession = userAResponse.readEntity(Session.class);
        TestUtils.validateSession(userASession);

        User userB = TestUser.generateUser(10);
        Response userBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session userBSession = userBResponse.readEntity(Session.class);
        TestUtils.validateSession(userBSession);

        String updatedName = "Batman";
        String updatedEmail = "batman@gotham.com";
        String updatedPassword = "222222";

        // Update Name
        Map<String, String> updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedName);
        Response userNameResponse = ClientUtil.putRequestWithAuth(userASession.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, userBSession.getUserId(), "name");
        TestUtils.assertForbidden(userNameResponse);

        Response updateNameDetailsResponse = ClientUtil.getRequestWithAuth(userBSession.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), userBSession.getUserId());
        User nameUpdatedUser = updateNameDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(userB.getName(), nameUpdatedUser.getName());

        // Update email
        updateNameMap = new HashMap<>();
        updateNameMap.put("value", updatedEmail);
        Response userEmailResponse = ClientUtil.putRequestWithAuth(userASession.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updateNameMap, userBSession.getUserId(), "email");
        TestUtils.assertForbidden(userEmailResponse);

        Response updateEmailDetailsResponse = ClientUtil.getRequestWithAuth(userBSession.getToken(), TestConstants.USER_GET_DETAILS, Optional.absent(), userBSession.getUserId());
        User emailUpdatedUser = updateEmailDetailsResponse.readEntity(User.class);
        TestUtils.assertEquals(userB.getEmail(), emailUpdatedUser.getEmail());

        // Update password
        Map<String, String> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("password", userB.getPassword());
        updatePasswordMap.put("password1", updatedPassword);
        updatePasswordMap.put("password2", updatedPassword);

        Response userPasswordResponse = ClientUtil.putRequestWithAuth(userASession.getToken(), TestConstants.USER_UPDATE_DETAILS, Optional.absent(), updatePasswordMap, userBSession.getUserId(), "password");
        TestUtils.assertForbidden(userPasswordResponse);

        Response passwordUpdatedUser = ClientUtil.getRequestWithBasicAuth(userB.getEmail(), userB.getPassword(), TestConstants.USER_GET_DETAILS, Optional.absent(), userBSession.getUserId());
        User updatedUser = passwordUpdatedUser.readEntity(User.class);
        TestUtils.assertEquals(userBSession.getUserId(), updatedUser.getId());
    }
}
