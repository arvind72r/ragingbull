/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class Access {

    private String getUserId;
    private String userId;
    private String code;
    private SystemConstants.AccessEntities entity;
    private Boolean active;
    private DateTime expiry;
    private DateTime created_at;
    private DateTime updated_at;

    public Access() {};

    public Access(String getUserId, String userId, String code, SystemConstants.AccessEntities entity, Boolean active, DateTime expiry) {
        this.getUserId = getUserId;
        this.userId = userId;
        this.code = code;
        this.entity = entity;
        this.active = active;
        this.expiry = expiry;
    }

    public Access(String getUserId, String userId, String code, SystemConstants.AccessEntities entity, Boolean active, DateTime expiry, DateTime created_at, DateTime updated_at) {
        this.getUserId = getUserId;
        this.userId = userId;
        this.code = code;
        this.entity = entity;
        this.active = active;
        this.expiry = expiry;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getGetUserId() {
        return getUserId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }

    public SystemConstants.AccessEntities getEntity() {
        return entity;
    }

    public Boolean getActive() {
        return active;
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
