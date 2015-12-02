/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.notification.Notifiable;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class NotificationConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("notifierMap")
    private Map<Notifiable.NotificationEvent, Notifiable.Mode[]> notifierMap = new HashMap<>();

    @Valid
    @NotNull
    @JsonProperty("twilio")
    private TwilioConfiguration twilioConfiguration = new TwilioConfiguration();

    @Valid
    @NotNull
    @JsonProperty("pusher")
    private PusherConfiguration pusherConfiguration = new PusherConfiguration();

    @Valid
    @NotNull
    @JsonProperty("mailChimp")
    private MailChimpConfiguration mailChimpConfiguration = new MailChimpConfiguration();

    @Valid
    @NotNull
    @JsonProperty("mandrillApp")
    private MandrillAppConfiguration mandrillAppConfiguration = new MandrillAppConfiguration();

    public void setNotifierMap(Map<String, Notifiable.Mode[]> notifierMap) {
        for (String key : notifierMap.keySet()) {
            this.notifierMap.put(Notifiable.NotificationEvent.valueOf(key), notifierMap.get(key));
        }
    }

    public Map<Notifiable.NotificationEvent, Notifiable.Mode[]> getNotifierMap() {
        return notifierMap;
    }

    public TwilioConfiguration getTwilioConfiguration() {
        return twilioConfiguration;
    }

    public PusherConfiguration getPusherConfiguration() {
        return pusherConfiguration;
    }

    public MailChimpConfiguration getMailChimpConfiguration() {
        return mailChimpConfiguration;
    }

    public MandrillAppConfiguration getMandrillAppConfiguration() {
        return mandrillAppConfiguration;
    }
}
