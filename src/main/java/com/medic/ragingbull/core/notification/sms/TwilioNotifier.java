/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.sms;

import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.exception.NotificationException;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
public class TwilioNotifier extends Notifiable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioNotifier.class);

    private final RagingBullConfiguration configuration;

    @Inject
    public TwilioNotifier(RagingBullConfiguration configuration) {
        this.configuration = configuration;

    }

    public void notify(User user, String body) throws NotificationException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Registration notification for user with email %s via SMS", user.getEmail()));
            }
            final TwilioRestClient client = new TwilioRestClient(this.configuration.getTwilioConfiguration().getAccountId(), this.configuration.getTwilioConfiguration().getToken());
            Account mainAccount = client.getAccount();
            final SmsFactory messageFactory = mainAccount.getSmsFactory();
            final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
            messageParams.add(new BasicNameValuePair("To", user.getPhone())); // Replace with a valid phone number
            messageParams.add(new BasicNameValuePair("From", configuration.getTwilioConfiguration().getRegisteredPhoneNo())); // Replace with a valid phone number in your account
            messageParams.add(new BasicNameValuePair("Body", body));
            messageFactory.create(messageParams);
        } catch(Exception e) {
            LOGGER.error("Error while sending registration notification to user %s", user.getEmail());
            throw new NotificationException(e);
        }
    }

    public void notifyAnonUser(String phone, String body) throws NotificationException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Registration notification for anon user with number %s via SMS", phone));
            }
            final TwilioRestClient client = new TwilioRestClient(this.configuration.getTwilioConfiguration().getAccountId(), this.configuration.getTwilioConfiguration().getToken());
            Account mainAccount = client.getAccount();
            final SmsFactory messageFactory = mainAccount.getSmsFactory();
            final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
            messageParams.add(new BasicNameValuePair("To", phone)); // Replace with a valid phone number
            messageParams.add(new BasicNameValuePair("From", configuration.getTwilioConfiguration().getRegisteredPhoneNo())); // Replace with a valid phone number in your account
            messageParams.add(new BasicNameValuePair("Body", body));
            messageFactory.create(messageParams);
        } catch(Exception e) {
            LOGGER.error("Error while sending registration notification to anon user %s", phone);
            throw new NotificationException(e);
        }
    }
}
