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
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prescription {

    @JsonProperty
    private String id;

    @JsonProperty
    private String consultationId;

    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String notes;
    private DateTime createdAt;
    private DateTime updatedAt;

    @Size(  max = ValidationConstants.DRUGS_MAX,
            min = ValidationConstants.DRUGS_MIN,
            message = ValidationConstants.DRUGS_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUGS_MSG_EMPTY)
    @JsonProperty
    private List<Drug> drugs;

    public Prescription(String id, String consultationId, String practitionerId, String locationId, String userId, String notes, DateTime createdAt, DateTime updatedAt, List<Drug> drugs) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.userId = userId;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.drugs = drugs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
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

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
