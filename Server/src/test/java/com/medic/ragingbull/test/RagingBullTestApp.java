/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.medic.ragingbull.RagingBullServer;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.access.service.UserAccessService;
import com.medic.ragingbull.core.services.*;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.annotations.SourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

/**
 * Created by Vamshi Molleti
 */
public abstract class RagingBullTestApp {

    public static DropwizardAppRule<RagingBullConfiguration> RULE;//  = new DropwizardAppRule<>(RagingBullServer.class, "/Users/vamshi/RagingBull/Server/input.yml");

    protected RagingBullServer app;

    //Configuration
    protected RagingBullConfiguration configuration;

    //Access service
    private UserAccessService userAccessService;
    // Services
    protected UserService userService;
    protected AuthService authService;
    protected OAuthService oAuthService;
    protected PractitionerService practitionerService;
    protected PharmacyService pharmacyService;
    protected PractitionerLocationService practitionerLocationService;
    protected ImageService imageService;
    protected ConsultationService consultationService;
    protected PrescriptionService prescriptionService;

    // Mappers
    protected ObjectMapper objectMapper;

    @ClassRule
    public static DropwizardAppRule classSetup() {

        RULE = new DropwizardAppRule<>(RagingBullServer.class, ResourceHelpers.resourceFilePath("test-config.yml"));

        return RULE;
    }

    @Before
    public void setupBaseApp() throws Exception {

        app = RULE.getApplication();

        app.run(new String[0]);
        //Configuration
        configuration = app.getService(RagingBullConfiguration.class);
        // Access service

        userAccessService = app.getService(UserAccessService.class);
        // Services
        userService= app.getService(UserService.class);
        authService = app.getService(AuthService.class);
        oAuthService = app.getService(OAuthService.class);
        practitionerService = app.getService(PractitionerService.class);
        pharmacyService = app.getService(PharmacyService.class);
        practitionerLocationService = app.getService(PractitionerLocationService.class);
        imageService = app.getService(ImageService.class);
        consultationService = app.getService(ConsultationService.class);
        prescriptionService = app.getService(PrescriptionService.class);

        // Create Jackson ObjectMapper
        objectMapper = new ObjectMapper();
        // Register the joda module
        objectMapper.registerModule(new JodaModule());
        // Register the guava module
        objectMapper.registerModule(new GuavaModule());
    }

    @After
    public void cleanup() {

    }
}
