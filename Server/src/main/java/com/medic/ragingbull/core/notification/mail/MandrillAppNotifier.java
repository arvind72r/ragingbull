/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.mail;

import com.google.inject.Inject;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.MailChimpConfiguration;
import com.medic.ragingbull.config.MandrillAppConfiguration;
import com.medic.ragingbull.core.notification.Notifiable;

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

    public void notify(String id) {

    }
}
