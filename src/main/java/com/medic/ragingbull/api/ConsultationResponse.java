/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vamshi Molleti
 */
public class ConsultationResponse extends AbstractResponse{

    @JsonProperty
    private String id;

    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer slot;

    @JsonProperty
    private String notes;

    public ConsultationResponse(String id, String practitionerId, String locationId, String userId, String name, Integer slot, String notes) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.userId = userId;
        this.name = name;
        this.slot = slot;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getSlot() {
        return slot;
    }

    public String getNotes() {
        return notes;
    }
}
