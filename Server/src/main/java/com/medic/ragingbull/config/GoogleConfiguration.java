/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Vamshi Molleti
 */
public class GoogleConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String clientId;

    @NotEmpty
    @JsonProperty
    private String secret;

    @NotEmpty
    @JsonProperty
    private String redirectUrl;

    @NotEmpty
    @JsonProperty
    private String applicationName;

    public String getClientId() {
        return clientId;
    }

    public String getSecret() {
        return secret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
