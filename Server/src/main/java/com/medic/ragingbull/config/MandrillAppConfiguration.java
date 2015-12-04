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
public class MandrillAppConfiguration  extends Configuration{

    @NotEmpty
    @JsonProperty
    private String url;

    @NotEmpty
    @JsonProperty
    private String applicationKey;

    @NotEmpty
    @JsonProperty
    private String senderName;

    @NotEmpty
    @JsonProperty
    private String senderEmail;

    public String getUrl() {
        return url;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }
}
