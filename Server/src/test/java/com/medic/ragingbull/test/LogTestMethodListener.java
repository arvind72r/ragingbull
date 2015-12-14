/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by Vamshi Molleti
 */
public class LogTestMethodListener extends RunListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogTestMethodListener.class);

    @Override
    public void testStarted(final Description description) throws Exception {
        LOGGER.info(description.getTestClass().getName() + "." + description.getMethodName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info(iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
