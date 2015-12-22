/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.mail;

import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.api.notification.Mail;
import com.medic.ragingbull.api.notification.MailConfig;
import com.medic.ragingbull.api.notification.MailDynamicContent;
import com.medic.ragingbull.config.MailChimpConfiguration;
import com.medic.ragingbull.config.MandrillAppConfiguration;
import com.medic.ragingbull.core.notification.Notifiable;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Vamshi Molleti
 */
public class MandrillAppNotifier extends Notifiable {

    private final MandrillAppConfiguration mandrillAppConfiguration;
    private final MailChimpConfiguration mailChimpConfiguration;

    @Inject
    public MandrillAppNotifier(MandrillAppConfiguration mandrillAppConfiguration, MailChimpConfiguration mailChimpConfiguration) {
        this.mandrillAppConfiguration = mandrillAppConfiguration;
        this.mailChimpConfiguration = mailChimpConfiguration;
    }

    public void notify(String id, NotificationEvent event, Object message) {
        if (event == NotificationEvent.SIGN_UP) {
            notifySignUp(id, String.valueOf(message));
        } else if (event == NotificationEvent.CONSULTATION_SUBMITTED) {

        }

    }

    private Boolean notifySignUp(String id, String message) {
//        MailDynamicContent dynamicContent = new MailDynamicContent("userName", message);
//        MailConfig config = new MailConfig("Welcome to RagingBull", )
//        Mail mail = new Mail(mandrillAppConfiguration.getApplicationKey(), "sign_up", dynamicContent, );
        return null;
    }
}
