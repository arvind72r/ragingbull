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
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consultation {

    @JsonProperty
    private String id;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String userId;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String creatorId;

    @JsonProperty
    private List<String> symptoms;

    @JsonProperty
    private List<String> diagnosisNotes;

    @JsonProperty
    private List<String> userNotes;

    @JsonProperty
    private Boolean active = Boolean.TRUE;

    private DateTime createdAt;
    private DateTime updatedAt;

    public Consultation () {};

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, List<String> symptoms, List<String> diagnosisNotes, List<String> userNotes, Boolean active) {
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
        this.symptoms = symptoms;
        this.diagnosisNotes = diagnosisNotes;
        this.userNotes = userNotes;
        this.active = active;
    }

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, List<String> symptoms, List<String> diagnosisNotes, List<String> userNotes, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
        this.symptoms = symptoms;
        this.diagnosisNotes = diagnosisNotes;
        this.userNotes = userNotes;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public List<String> getDiagnosisNotes() {
        return diagnosisNotes;
    }

    public List<String> getUserNotes() {
        return userNotes;
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
