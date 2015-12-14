/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.TestUser;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import io.dropwizard.client.JerseyClientBuilder;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class RegistrationResourceTest extends RagingBullTestApp{

    @Test
    public void testUserRegistration() {
        // Create user
        User user  = TestUser.generateUser();

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        Response response = client.target(
                String.format(TestConstants.USER_REGISTRATION_URL, RULE.getLocalPort()))
                .request()
                .post(Entity.json(user));

        //Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);

        System.out.println(response.getEntity());

    }
}
