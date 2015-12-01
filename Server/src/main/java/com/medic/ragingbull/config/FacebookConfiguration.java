/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Vamshi Molleti
 */
public class FacebookConfiguration {

    @NotEmpty
    @JsonProperty
    private String clientId;

    @NotEmpty
    @JsonProperty
    private String secret;

    @NotEmpty
    @JsonProperty
    private String redirectUrl;

    public String getClientId() {
        return clientId;
    }

    public String getSecret() {
        return secret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
