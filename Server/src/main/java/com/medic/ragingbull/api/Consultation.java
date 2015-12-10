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

import java.util.ArrayList;
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
    private List<String> symptoms = new ArrayList<>();

    @JsonProperty
    private List<String> diagnosis = new ArrayList<>();

    @JsonProperty
    private List<String> userNotes = new ArrayList<>();

    @JsonProperty
    private Boolean active = Boolean.TRUE;

    private String userName;

    private String userAge;

    private String userPhone;

    private String practitionerName;


    private DateTime createdAt;
    private DateTime updatedAt;

    public Consultation () {};

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, Boolean active, String userName, String userAge, String userPhone, String practitionerName, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
        this.active = active;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.practitionerName = practitionerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
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

    public List<String> getDiagnosis() {
        return diagnosis;
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

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getPractitionerName() {
        return practitionerName;
    }
}
