/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class RegistrationResponse {

    private String id;
    private String email;
    private String inviteId;
    private long expiry;

    public RegistrationResponse(String id, String email, String inviteId, long expiry) {
        this.id = id;
        this.email = email;
        this.inviteId = inviteId;
        this.expiry = expiry;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getInviteId() {
        return inviteId;
    }

    public long getExpiry() {
        return expiry;
    }
}
