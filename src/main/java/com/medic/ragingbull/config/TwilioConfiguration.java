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
public class TwilioConfiguration extends Configuration{

    @NotEmpty
    @JsonProperty
    private String accountId;

    @NotEmpty
    @JsonProperty
    private String token;

    @NotEmpty
    @JsonProperty
    private String registeredPhoneNo;

    public String getAccountId() {
        return accountId;
    }

    public String getToken() {
        return token;
    }

    public String getRegisteredPhoneNo() {
        return registeredPhoneNo;
    }
}
