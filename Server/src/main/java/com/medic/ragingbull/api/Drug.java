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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drug {

    private String id;

    @JsonProperty
    private String consultationId;

    @JsonProperty
    private String practitionerId;

    @JsonProperty
    private String prescriptionId;

    @JsonProperty
    private String userId;

    @Length(min = ValidationConstants.NAME_MIN,
            max = ValidationConstants.NAME_MAX,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private Integer frequency = 0;

    @NotEmpty(message = ValidationConstants.DRUG_FREQUENCY_MSG_EMPTY)
    @JsonProperty
    private Dosage.Schedule schedule;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private Integer dose;

    @NotEmpty(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String unit;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private Integer days;

    @JsonProperty
    private Boolean active;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private DateTime updatedAt;

    public Drug() {};

    public Drug(String name, Integer frequency, Dosage.Schedule schedule, Integer dose, String unit, Integer days) {
        this.name = name;
        this.frequency = frequency;
        this.schedule = schedule;
        this.dose = dose;
        this.unit = unit;
        this.days = days;
    }

    public Drug(String id, String consultationId, String practitionerId, String prescriptionId, String userId, String name, Integer frequency, Dosage.Schedule schedule, Integer dose, String unit, Integer days, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.prescriptionId = prescriptionId;
        this.userId = userId;
        this.name = name;
        this.frequency = frequency;
        this.schedule = schedule;
        this.dose = dose;
        this.unit = unit;
        this.days = days;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Dosage.Schedule getSchedule() {
        return schedule;
    }

    public Integer getDose() {
        return dose;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getDays() {
        return days;
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
