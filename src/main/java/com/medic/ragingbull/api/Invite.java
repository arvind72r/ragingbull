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
public class Invite {

    private String inviteId;
    private String userId;
    private Integer code;
    private DateTime expiry;
    private DateTime created_at;
    private DateTime updated_at;


    public Invite(String inviteId, String userId, Integer code, DateTime expiry, DateTime created_at, DateTime updated_at) {
        this.inviteId = inviteId;
        this.userId = userId;
        this.code = code;
        this.expiry = expiry;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getInviteId() {
        return inviteId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getCode() {
        return code;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public DateTime getCreated_at() {
        return created_at;
    }

    public DateTime getUpdated_at() {
        return updated_at;
    }
}
