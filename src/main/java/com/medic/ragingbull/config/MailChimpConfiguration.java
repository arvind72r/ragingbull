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
public class MailChimpConfiguration extends Configuration{

    @NotEmpty
    @JsonProperty
    private String url;

    @NotEmpty
    @JsonProperty
    private String applicationKey;

    public String getUrl() {
        return url;
    }

    public String getApplicationKey() {
        return applicationKey;
    }
}
