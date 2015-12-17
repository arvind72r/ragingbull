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
import com.medic.ragingbull.core.services.*;
import com.medic.ragingbull.jdbi.dao.AccessDao;
import com.medic.ragingbull.jdbi.dao.SessionsDao;
import com.medic.ragingbull.jdbi.dao.UsersDao;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

/**
 * Created by Vamshi Molleti
 */
public abstract class RagingBullTestApp {

    public static DropwizardAppRule<RagingBullConfiguration> RULE;

    protected RagingBullServer app;

    //Configuration
    protected RagingBullConfiguration configuration;


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

    // DAOs
    private UsersDao usersDao;
    private SessionsDao sessionsDao;
    private AccessDao accessDao;

    // Mappers
    protected ObjectMapper objectMapper;

    @ClassRule
    public static DropwizardAppRule classSetup() {

        RULE  = new DropwizardAppRule<>(RagingBullServer.class, resourceFilePath("test-config.yml"));;

        return RULE;
    }

    @Before
    public void setupBaseApp() {

        app = RULE.getApplication();


        //Configuration
        configuration = app.getService(RagingBullConfiguration.class);

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

        // DAOs
        usersDao = app.getService(UsersDao.class);
        sessionsDao = app.getService(SessionsDao.class);
        accessDao = app.getService(AccessDao.class);


        // Create Jackson ObjectMapper
        objectMapper = new ObjectMapper();
        // Register the joda module
        objectMapper.registerModule(new JodaModule());
        // Register the guava module
        objectMapper.registerModule(new GuavaModule());
    }

    @After
    public void cleanup() throws IOException {
        // Delete all data from tables
        usersDao.cleanseAll();
        sessionsDao.cleanseAll();
        accessDao.cleanseAll();
        // Remove the temp database created.
        //Files.deleteIfExists(Paths.get("ragingbull_test.mv.db"));
    }
}
