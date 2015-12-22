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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consultation {

    @JsonProperty
    private String id;

    @JsonProperty
    private String userId;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String creatorId;

    @JsonProperty
    private String symptoms;

    @JsonProperty
    private String diagnosis;

    @JsonProperty
    private String userNotes;

    @JsonProperty
    private Boolean active = Boolean.TRUE;

    private String userName;

    private String userAge;

    private String userPhone;

    private String practitionerName;

    private String createdAt;

    private String updatedAt;

    public Consultation () {};

    public Consultation(String practitionerId, String symptoms) {
        this.practitionerId = practitionerId;
        this.symptoms = symptoms;
    }

    public Consultation(String userId, String practitionerId, String symptoms) {
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.symptoms = symptoms;
    }

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, String symptoms, String diagnosis, String userNotes, Boolean active, String name, DateTime dob, String phone, String practitionerName, DateTime createdAt, DateTime updatedAt) {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MMM-YYYY");
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.userNotes = userNotes;
        this.active = active;
        this.userName = name;
        this.userAge = String.valueOf(new DateTime().getYear() - dob.getYear());
        this.userPhone = phone;
        this.practitionerName = practitionerName;
        this.createdAt = createdAt.toString(format);
        this.updatedAt = updatedAt.toString(format);;
    }

    public Consultation(String id, String userId, String practitionerId, String locationId, String creatorId, Boolean active, String symptoms, String diagnosis, String userNotes, DateTime createdAt, DateTime updatedAt) {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MMM-YYYY");
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.creatorId = creatorId;
        this.active = active;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.userNotes = userNotes;
        this.createdAt = createdAt.toString(format);
        this.updatedAt = updatedAt.toString(format);;
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

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public Boolean getActive() {
        return active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
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
