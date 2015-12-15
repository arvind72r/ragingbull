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
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */

public class RegistrationResourceTest extends RagingBullTestApp{

    @Test
    public void userRegistrationTest() throws RagingBullBaseException {

        // Create user
        User user  = TestUser.generateUser();
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        // Check if able to resend InviteAuthCode

        Response resendInviteCode = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_RESEND_AUTH_CODE, Optional.absent(), session.getUserId());

        TestUtils.assertValidResponse(resendInviteCode);

        // Check approved user
        String authCode = userService.resendInviteAuthCode(session, session.getUserId());

        Response userApproveResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.USER_AUTH_CODE_APPROVE, Optional.absent(), Optional.absent(), authCode);
        TestUtils.assertValidResponse(userApproveResponse);

        // Fetch the user again and check if his verified flag is set to true
        Response userDetailsResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.USER_GET_DETAILS_SELF, Optional.absent());
        TestUtils.assertValidResponse(userApproveResponse);
        User userDetails = userDetailsResponse.readEntity(User.class);
        TestUtils.validateVerifiedUser(userDetails);
    }
}
