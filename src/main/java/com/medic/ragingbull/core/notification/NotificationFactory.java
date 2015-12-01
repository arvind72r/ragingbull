/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification;

import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.notification.mail.MandrillAppNotifier;
import com.medic.ragingbull.core.notification.push.PusherNotifier;
import com.medic.ragingbull.core.notification.sms.TwilioNotifier;
import com.medic.ragingbull.exception.NotificationException;

/**
 * Created by Vamshi Molleti
 */
public class NotificationFactory {

    private MandrillAppNotifier mandrillAppNotifier;
    private PusherNotifier pusherNotifier;
    private TwilioNotifier twilioNotifier;

    @Inject
    public NotificationFactory(MandrillAppNotifier mandrillAppNotifier, PusherNotifier pusherNotifier, TwilioNotifier twilioNotifier) {
        this.mandrillAppNotifier = mandrillAppNotifier;
        this.pusherNotifier = pusherNotifier;
        this.twilioNotifier = twilioNotifier;
    }

    public void notifyUser(User user, Notifiable.Mode mode, String message) throws NotificationException {
            switch (mode) {
                case MAIL:
                    mandrillAppNotifier.notify(user);
                    break;
                case PUSH:
                    pusherNotifier.notify(user, message);
                    break;
                case SMS:
                    twilioNotifier.notify(user, String.format(SystemConstants.SMS_REGISTRATION_TOKEN, message));
                    break;
        }
    }

//    public void notifyUser(User user, Notifiable.Event event, String message) throws NotificationException {
//        switch (mode) {
//            case MAIL:
//                mandrillAppNotifier.notify(user);
//                break;
//            case PUSH:
//                pusherNotifier.notify(user, message);
//                break;
//            case SMS:
//                twilioNotifier.notify(user, String.format(SystemConstants.SMS_REGISTRATION_TOKEN, message));
//                break;
//        }
//    }


}
