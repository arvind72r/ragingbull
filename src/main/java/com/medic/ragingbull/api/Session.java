/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class Session {

    private String token;
    private String userId;
    private String data;
    private DateTime createdAt;
    private DateTime expiry;

    public Session(String token, String userId, String data, DateTime createdAt, DateTime expiry) {
        this.token = token;
        this.userId = userId;
        this.data = data;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getData() {
        return data;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getExpiry() {
        return expiry;
    }
}
