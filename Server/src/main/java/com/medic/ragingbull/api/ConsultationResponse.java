/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String symptoms;

    @JsonProperty
    private String diagnosisNotes;

    @JsonProperty
    private String userNotes;

    @JsonProperty
    private Boolean active;

    private String userName;

    private String userAge;

    private String userPhone;

    private String practitionerName;

    public ConsultationResponse() {};

    public ConsultationResponse(String id, String practitionerId, String locationId, String userId, String symptoms, String diagnosisNotes, String
            userNotes, Boolean active, String userName, String userAge, String userPhone, String practitionerName) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.userId = userId;
        this.symptoms = symptoms;
        this.diagnosisNotes = diagnosisNotes;
        this.userNotes = userNotes;
        this.active = active;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.practitionerName = practitionerName;
    }

    public ConsultationResponse(String id, String practitionerId, String locationId, String userId, String symptoms, String diagnosisNotes, String userNotes, Boolean active) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.locationId = locationId;
        this.userId = userId;
        this.symptoms = symptoms;
        this.diagnosisNotes = diagnosisNotes;
        this.userNotes = userNotes;
        this.active = active;
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

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosisNotes() {
        return diagnosisNotes;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public Boolean getActive() {
        return active;
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
