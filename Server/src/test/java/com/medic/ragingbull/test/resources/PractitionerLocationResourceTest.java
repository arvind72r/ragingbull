/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestPractitioner;
import com.medic.ragingbull.test.generator.TestPractitionerLocation;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationResourceTest  extends RagingBullTestApp{

    @Test
    public void testCreatePractitionerLocation() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocation = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponse = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocation, practitioner.getId());

        TestUtils.assertValidResponse(practitionerLocationResponse);
    }

    @Test
    public void testCreateMultiplePractitionerLocation() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());

        TestUtils.assertValidResponse(practitionerLocationResponseA);

        PractitionerLocation practitionerLocationB = TestPractitionerLocation.createPractitionerLocation(1);
        Response practitionerLocationResponseB = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationB, practitioner.getId());

        TestUtils.assertValidResponse(practitionerLocationResponseB);

        PractitionerLocation practitionerLocationC = TestPractitionerLocation.createPractitionerLocation(2);
        Response practitionerLocationResponseC = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationC, practitioner.getId());

        TestUtils.assertValidResponse(practitionerLocationResponseC);
    }

    @Test
    public void testGetPractitionerLocation() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);


        Response practitionerLocationResponseFetch = ClientUtil.getRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_GET, Optional.absent(), practitioner.getId(), practitionerLocation.getId());
        PractitionerLocationResponse fetchPractitionerLocation = practitionerLocationResponseFetch.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseFetch);
        TestUtils.assertTrue(practitionerLocation.equals(fetchPractitionerLocation));
    }
}
