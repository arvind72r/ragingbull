/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification.push;

import com.google.inject.Inject;
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

    @Inject
    public PusherNotifier(RagingBullConfiguration ragingBullConfiguration) {
        this.configuration = ragingBullConfiguration.getNotificationConfiguration().getPusherConfiguration();
        pusher = new Pusher(configuration.getApplicationId(), configuration.getApplicationKey(), configuration.getApplicationSecret());
    }

    public void notify(String id, NotificationEvent event, String message) {
        String channelName = event.name() + id;
        pusher.trigger(channelName, event.name(), message);
    }
}
