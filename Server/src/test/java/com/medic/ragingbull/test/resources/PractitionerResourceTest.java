/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.api.PractitionerResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestPractitioner;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerResourceTest extends RagingBullTestApp{

    @Test
    public void testCreatePractitioner() {

        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants.PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        TestUtils.assertValidResponse(practitionerResponse);
    }

    @Test
    public void testGetPractitioner() {

        User user = TestUser.generateUser(1);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(1);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        Response getPractitionerResponse = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_GET, Optional.absent(), practitioner.getId());
        PractitionerResponse fetchedPractitioner = getPractitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(getPractitionerResponse);
        TestUtils.assertTrue(practitioner.equals(fetchedPractitioner));
    }

    @Test
    public void testCreatePractitionerDuplicate() {

        User user = TestUser.generateUser(2);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(2);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        TestUtils.assertValidResponse(practitionerResponse);

        Response duplicateResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants.PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        TestUtils.assertConflict(duplicateResponse);
    }

    @Test
    public void testGetPractitionerDifferentIds() {

        // Create different users
        User userA = TestUser.generateUser(3);
        Response userAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session userASession = userAResponse.readEntity(Session.class);
        TestUtils.validateSession(userASession);

        User userB = TestUser.generateUser(4);
        Response userBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session userBSession = userBResponse.readEntity(Session.class);
        TestUtils.validateSession(userBSession);


        // Create different practitioners
        Practitioner testPractitionerA = TestPractitioner.generatePractitioner(3);
        Response practitionerResponseA = ClientUtil.postRequestWithAuth(userASession.getToken(), TestConstants.PRACTITIONER_ADD, Optional.absent(), testPractitionerA);
        PractitionerResponse practitionerA = practitionerResponseA.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponseA);

        Practitioner testPractitionerB = TestPractitioner.generatePractitioner(4);
        Response practitionerResponseB = ClientUtil.postRequestWithAuth(userBSession.getToken(), TestConstants.PRACTITIONER_ADD, Optional.absent(), testPractitionerB);
        PractitionerResponse practitionerB = practitionerResponseB.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponseB);

        // Fetch created practitioners and compare with response while registering
        Response getPractitionerAResponse = ClientUtil.getRequestWithAuth(userASession.getToken(), TestConstants
                        .PRACTITIONER_GET, Optional.absent(), practitionerA.getId());
        PractitionerResponse fetchedPractitionerA = getPractitionerAResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(getPractitionerAResponse);
        TestUtils.assertTrue(practitionerA.equals(fetchedPractitionerA));

        Response getPractitionerBResponse = ClientUtil.getRequestWithAuth(userBSession.getToken(), TestConstants
                        .PRACTITIONER_GET, Optional.absent(), practitionerB.getId());
        PractitionerResponse fetchedPractitionerB = getPractitionerBResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(getPractitionerBResponse);
        TestUtils.assertTrue(practitionerB.equals(fetchedPractitionerB));

        // Try to get credentials for practitionerB by practitionerA
        Response spoofedAResponse = ClientUtil.getRequestWithAuth(userASession.getToken(), TestConstants
                        .PRACTITIONER_GET, Optional.absent(), practitionerB.getId());
        TestUtils.assertForbidden(spoofedAResponse);
        Response spoofedBResponse = ClientUtil.getRequestWithAuth(userBSession.getToken(), TestConstants
                        .PRACTITIONER_GET, Optional.absent(), practitionerA.getId());
        TestUtils.assertForbidden(spoofedBResponse);
    }
}
