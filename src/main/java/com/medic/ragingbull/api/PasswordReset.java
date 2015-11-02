/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordReset {

    @JsonProperty
    private String id;

    @JsonProperty
    private String email;

    @JsonProperty
    private Boolean active;

    @JsonProperty
    private DateTime expiry;

    @JsonIgnoreProperties
    private DateTime createdAt;
    @JsonIgnoreProperties
    private DateTime updatedAt;

    public PasswordReset(String id, String email, Boolean active, DateTime expiry, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.active = active;
        this.expiry = expiry;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PasswordReset(String id, String email, DateTime expiry) {
        this.id = id;
        this.email = email;
        this.expiry = expiry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(DateTime expiry) {
        this.expiry = expiry;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
