/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class LoginResponse {

    @JsonProperty
    private String token;

    @JsonProperty
    private String userEmail;

    @JsonProperty
    private Boolean isUserVerified;

    @JsonProperty
    private DateTime expiry;

    public LoginResponse(String token, String userEmail, Boolean isUserVerified, DateTime expiry) {
        this.token = token;
        this.userEmail = userEmail;
        this.isUserVerified = isUserVerified;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Boolean getIsUserVerified() {
        return isUserVerified;
    }

    public DateTime getExpiry() {
        return expiry;
    }
}
