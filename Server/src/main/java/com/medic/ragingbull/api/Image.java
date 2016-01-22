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
public class Image {

    private String id;
    private String userId;
    private String type;
    private String path;
    private String data;
    private DateTime createdAt;
    private DateTime updatedAt;

    public Image(String id, String userId, String type, String path, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}
