/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;


import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.access.roles.UserRoles;
import org.junit.Assert;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class TestUtils {


    public static void assertValidResponse(Response response) {
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    public static void validateSession(Session session) {
        // Check if valid object
        Assert.assertNotNull(session);

        Assert.assertNotNull(session.getToken());
        Assert.assertNotNull(session.getUserId());
        Assert.assertNotNull(session.getUserEmail());
        Assert.assertNotNull(session.getPhone());
        Assert.assertFalse(session.getIsUserValid());
        Assert.assertEquals(session.getRole().longValue(), UserRoles.Role.NATIVE_USER.getRoleBit());
    }

    public static void validateVerifiedUser(User user) {
        // Check if verified flag is set to true
        Assert.assertTrue(user.getVerified());

    }

    public static void assertConflict(Response response) {
        Assert.assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    public static void assertMissingMandatory(Response response) {
        Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    public static void assertUnProcessableEntity(Response response) {
        Assert.assertEquals(422, response.getStatus());
    }

    public static void assertEquals(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }

    public static void assertTrue(Boolean expected, Boolean actual) {
        Assert.assertTrue((expected == actual));

    }

    public static void assertEquals(Session session, User user) {
        Assert.assertEquals(session.getUserId(), user.getId());
        Assert.assertEquals(session.getUserEmail(), user.getEmail());
        Assert.assertEquals(session.getPhone(), user.getPhone());
    }
}
