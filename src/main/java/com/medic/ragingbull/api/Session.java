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
    private String phone;
    private Integer role;
    private Boolean isUserValid;
    private DateTime createdAt;
    private DateTime expiry;

    public Session(String token, String userId, String userEmail, Integer role, DateTime createdAt, DateTime expiry) {
        this.token = token;
        this.userEmail = userEmail;
        this.userId = userId;
        this.role = role;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

    public void setIsUserValid(Boolean isUserValid) {
        this.isUserValid = isUserValid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public Boolean getIsUserValid() {
        return isUserValid;
    }

    public String getPhone() {
        return phone;
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
