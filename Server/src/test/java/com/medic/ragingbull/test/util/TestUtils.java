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


    public static void assertValidResponse(Response resendInviteCode) {
        Assert.assertEquals(Response.Status.OK.getStatusCode(), resendInviteCode.getStatus());
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
}
