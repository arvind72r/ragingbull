/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test;

import com.medic.ragingbull.test.resources.RegistrationResourceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
//@RunWith(Suite.class)
//@Suite.SuiteClasses(RegistrationResourceTest.class)
public class AllRagingBullAPITests {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AllRagingBullAPITests.class);

    @BeforeClass
    public static void setUpClass()
            throws Exception {
        LOGGER.info("Starting Tests...");
        LOGGER.info("Working directory: {}", System.getProperty("user.dir"));
    }

    @AfterClass
    public static void tearDownClass()
            throws Exception {
        LOGGER.info("Stopping Tests...");
    }
}
