/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.sms;

import com.google.inject.Inject;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.config.TwilioConfiguration;
import com.medic.ragingbull.core.notification.Notifiable;
import com.medic.ragingbull.exception.NotificationException;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class TwilioNotifier extends Notifiable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioNotifier.class);

    private final TwilioConfiguration configuration;

    @Inject
    public TwilioNotifier(RagingBullConfiguration configuration) {
        this.configuration = configuration.getNotificationConfiguration().getTwilioConfiguration();

    }

    public void notify(String id, String body) throws NotificationException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Registration notification for user with id %s via SMS", id));
            }
            final TwilioRestClient client = new TwilioRestClient(configuration.getAccountId(), configuration.getToken());
            Account mainAccount = client.getAccount();
            final SmsFactory messageFactory = mainAccount.getSmsFactory();
            final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
            messageParams.add(new BasicNameValuePair("To", id)); // Replace with a valid phone number
            messageParams.add(new BasicNameValuePair("From", configuration.getRegisteredPhoneNo())); // Replace with a valid phone number in your account
            messageParams.add(new BasicNameValuePair("Body", body));
            messageFactory.create(messageParams);
        } catch(Exception e) {
            LOGGER.error("Error while sending registration notification to id %s", id);
            throw new NotificationException(e);
        }
    }
}
