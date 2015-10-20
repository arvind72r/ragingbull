/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class Invite {
    private String inviteId;
    private String userId;
    private DateTime expiry;
    private DateTime createdAt;
    private DateTime updatedAt;

    public Invite(String inviteId, String userId, DateTime expiry, DateTime createdAt, DateTime updatedAt) {
        this.inviteId = inviteId;
        this.userId = userId;
        this.expiry = expiry;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(DateTime expiry) {
        this.expiry = expiry;
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
