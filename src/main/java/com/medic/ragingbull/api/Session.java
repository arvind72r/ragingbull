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
    private String userEmail;
    private String userId;
    private DateTime createdAt;
    private DateTime expiry;

    public Session(String token, String userEmail, String userId, DateTime createdAt, DateTime expiry) {
        this.token = token;
        this.userEmail = userEmail;
        this.userId = userId;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getExpiry() {
        return expiry;
    }
}
