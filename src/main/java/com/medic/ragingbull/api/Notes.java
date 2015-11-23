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
public class Notes {

    private String id;
    private String entityId;
    private SystemConstants.NotesTypes entityType;
    private String content;
    private Boolean active = Boolean.TRUE;
    private DateTime createdAt;
    private DateTime updatedAt;

    public Notes() {}

    public Notes(String id, String entityId, SystemConstants.NotesTypes entityType, String content) {
        this.id = id;
        this.entityId = entityId;
        this.entityType = entityType;
        this.content = content;
    }

    public Notes(String id, String entityId, SystemConstants.NotesTypes entityType, String content, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.entityId = entityId;
        this.entityType = entityType;
        this.content = content;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getEntityId() {
        return entityId;
    }

    public SystemConstants.NotesTypes getEntityType() {
        return entityType;
    }

    public String getContent() {
        return content;
    }

    public Boolean getActive() {
        return active;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}
