/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.push;

import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.PusherConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.notification.Notifiable;
import com.pusher.rest.Pusher;

/**
 * Created by Vamshi Molleti
 */
public class PusherNotifier extends Notifiable {

    private final PusherConfiguration configuration;
    private final Pusher pusher;
    public PusherNotifier(RagingBullConfiguration ragingBullConfiguration) {
        this.configuration = ragingBullConfiguration.getPusherConfiguration();
        pusher = new Pusher(configuration.getApplicationId(), configuration.getApplicationKey(), configuration.getApplicationSecret());
    }

    public void notify(User user, String message) {
        pusher.trigger(user.getPhone(), configuration.getUserChannel(), message);


    }
}
