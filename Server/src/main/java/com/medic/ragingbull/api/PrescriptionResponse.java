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
public class PrescriptionResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private String consultationId;

    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String userId;

    @JsonProperty
    private List<Drug> drugs;

    @JsonProperty
    private Boolean active;

    public PrescriptionResponse() {};

    public PrescriptionResponse(String id, String consultationId, String practitionerId, String userId, List<Drug> drugs, Boolean active) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.drugs = drugs;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getUserId() {
        return userId;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public Boolean getActive() {
        return active;
    }
}
