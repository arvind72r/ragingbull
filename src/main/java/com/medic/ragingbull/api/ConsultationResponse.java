/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
    private List<String> symptoms;

    @JsonProperty
    private List<String> diagnosisNotes;

    @JsonProperty
    private List<String> userNotes;

    @JsonProperty
    private Boolean active;

    public ConsultationResponse() {};

    public ConsultationResponse(String id, String practitionerId, String locationId, String userId, List<String> symptoms, List<String> diagnosisNotes, List<String> userNotes, Boolean active) {
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
}
