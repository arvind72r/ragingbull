/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification;

import com.google.inject.Inject;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.mail.MandrillAppNotifier;
import com.medic.ragingbull.core.notification.push.PusherNotifier;
import com.medic.ragingbull.core.notification.sms.TwilioNotifier;
import com.medic.ragingbull.exception.NotificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class NotificationFactory extends Notifiable{

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFactory.class);

    private MandrillAppNotifier mandrillAppNotifier;
    private PusherNotifier pusherNotifier;
    private TwilioNotifier twilioNotifier;

    @Inject
    public NotificationFactory(MandrillAppNotifier mandrillAppNotifier, PusherNotifier pusherNotifier, TwilioNotifier twilioNotifier) {
        this.mandrillAppNotifier = mandrillAppNotifier;
        this.pusherNotifier = pusherNotifier;
        this.twilioNotifier = twilioNotifier;
    }

    public void notifyUser(String id, Mode mode, NotificationEvent event, String message) throws NotificationException {
            switch (mode) {
                case MAIL:
                    mandrillAppNotifier.notify(id);
                    break;
                case PUSH:
                    pusherNotifier.notify(id, event,  message);
                    break;
                case SMS:
                    twilioNotifier.notify(id, String.format(SystemConstants.SMS_REGISTRATION_TOKEN, message));
                    break;
        }
    }
}
