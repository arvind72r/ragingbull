/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NotEmpty(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String userId;

    @JsonProperty
    private List<String> notes;

    @Size(  max = ValidationConstants.DRUGS_MAX,
            min = ValidationConstants.DRUGS_MIN,
            message = ValidationConstants.DRUGS_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUGS_MSG_EMPTY)
    @JsonProperty
    private List<Drug> drugs;

    @JsonProperty
    private Boolean active;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private DateTime updatedAt;

    public Prescription(String id, String consultationId, String practitionerId, String userId, List<String> notes, List<Drug> drugs) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.notes = notes;
        this.drugs = drugs;
    }

    public Prescription(String id, String consultationId, String practitionerId, String userId, List<String> notes, List<Drug> drugs, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.notes = notes;
        this.drugs = drugs;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public List<String> getNotes() {
        return notes;
    }

    public List<Drug> getDrugs() {
        return drugs;
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
