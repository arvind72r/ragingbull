/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consultation {

    @JsonProperty
    private String id;

    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String userId;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotBlank(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @NotNull(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private Integer slot;

    @JsonProperty
    private String notes;

    @JsonProperty
    private Boolean active;

    private DateTime createdAt;
    private DateTime updatedAt;

    public Consultation(String id, String practitionerId, String locationId, String userId, String name, Integer slot, String notes, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.userId = userId;
        this.name = name;
        this.slot = slot;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
