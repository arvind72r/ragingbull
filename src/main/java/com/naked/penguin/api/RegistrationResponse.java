/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

/**
 * Created by Vamshi Molleti
 */
public class RegistrationResponse {

    private String name;
    private String email;
    private String inviteId;
    private long expiry;

    public RegistrationResponse(String name, String email, String inviteId, long expiry) {
        this.name = name;
        this.email = email;
        this.inviteId = inviteId;
        this.expiry = expiry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }
}
