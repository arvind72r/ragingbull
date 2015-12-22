/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification;

import com.google.inject.Inject;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.mail.MandrillAppNotifier;
import com.medic.ragingbull.core.notification.push.PusherNotifier;
import com.medic.ragingbull.core.notification.sms.TwilioNotifier;
import com.medic.ragingbull.exception.NotificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class NotificationFactory extends Notifiable{

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFactory.class);

    private Map<NotificationEvent, Mode[]> notificationEventMap;
    private MandrillAppNotifier mandrillAppNotifier;
    private PusherNotifier pusherNotifier;
    private TwilioNotifier twilioNotifier;

    @Inject
    public NotificationFactory(RagingBullConfiguration configuration, MandrillAppNotifier mandrillAppNotifier, PusherNotifier pusherNotifier, TwilioNotifier twilioNotifier) {
        this.notificationEventMap = configuration.getNotificationConfiguration().getNotifierMap();
        this.mandrillAppNotifier = mandrillAppNotifier;
        this.pusherNotifier = pusherNotifier;
        this.twilioNotifier = twilioNotifier;
    }

    public void notifyUser(String id, NotificationEvent event, Object message) throws NotificationException {
            for (Mode mode : notificationEventMap.get(event)) {
                switch (mode) {
                    case MAIL:
                        mandrillAppNotifier.notify(id, event, message);
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
}
