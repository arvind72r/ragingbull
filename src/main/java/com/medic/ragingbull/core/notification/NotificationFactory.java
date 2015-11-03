/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification;

import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.mail.MailGunNotifier;
import com.medic.ragingbull.core.notification.push.PusherNotifier;
import com.medic.ragingbull.core.notification.sms.TwilioNotifier;
import com.medic.ragingbull.exception.NotificationException;

/**
 * Created by Vamshi Molleti
 */
public class NotificationFactory {

    private MailGunNotifier mailGunNotifier;
    private PusherNotifier pusherNotifier;
    private TwilioNotifier twilioNotifier;

    @Inject
    public NotificationFactory(MailGunNotifier mailGunNotifier, PusherNotifier pusherNotifier, TwilioNotifier twilioNotifier) {
        this.mailGunNotifier = mailGunNotifier;
        this.pusherNotifier = pusherNotifier;
        this.twilioNotifier = twilioNotifier;
    }

    public void notifyUser(User user, Notifiable.Mode mode, int authCode) throws NotificationException {
            switch (mode) {
                case MAIL:
                    mailGunNotifier.notify(user);
                    break;
                case PUSH:
                    pusherNotifier.notify(user);
                    break;
                case SMS:
                    twilioNotifier.notify(user, String.format(SystemConstants.SMS_REGISTRATION_TOKEN, authCode));
                    break;
        }
    }

}
