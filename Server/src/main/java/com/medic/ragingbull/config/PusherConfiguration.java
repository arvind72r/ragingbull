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
public class PusherConfiguration extends Configuration{

    @NotEmpty
    @JsonProperty
    private String applicationId;

    @NotEmpty
    @JsonProperty
    private String applicationKey;

    @NotEmpty
    @JsonProperty
    private String applicationSecret;

    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public String getApplicationSecret() {
        return applicationSecret;
    }
}
