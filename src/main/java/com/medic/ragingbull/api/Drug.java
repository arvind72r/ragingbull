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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private String userId;

    @Length(min = ValidationConstants.NAME_MIN,
            max = ValidationConstants.NAME_MAX,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @Length(min = ValidationConstants.MANUFACTURER_NAME_MIN,
            max = ValidationConstants.MANUFACTURER_NAME_MAX,
            message = ValidationConstants.MANUFACTURER_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.MANUFACTURER_NAME_MSG_EMPTY)
    @JsonProperty
    private String manufacturer;



    @Min(ValidationConstants.DRUG_QUANTITY_MIN)
    @NotNull(message = ValidationConstants.DRUG_QUANTITY_MSG_EMPTY)
    @JsonProperty
    private Integer quantity;

    @Size(  max = ValidationConstants.DRUG_FREQUENCY_MAX,
            min = ValidationConstants.DRUG_FREQUENCY_MIN,
            message = ValidationConstants.DRUG_FREQUENCY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUG_FREQUENCY_MSG_EMPTY)
    @JsonProperty
    private Dosage.Frequency[] frequency;

    @Length(min = ValidationConstants.DRUG_ALLERGY_MIN,
            max = ValidationConstants.DRUG_ALLERGY_MAX,
            message = ValidationConstants.DRUG_ALLERGY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUG_ALLERGY_MSG_EMPTY)
    @JsonProperty
    private String allergies;

    @JsonProperty
    private Boolean active;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private DateTime updatedAt;

    public Drug(String id, String consultationId, String practitionerId, String userId, String name, String manufacturer, Integer quantity, Dosage.Frequency[] frequency, String allergies) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.frequency = frequency;
        this.allergies = allergies;
    }

    public Drug(String id, String consultationId, String practitionerId, String userId, String name, String manufacturer, Integer quantity, Dosage.Frequency[] frequency, String allergies, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.frequency = frequency;
        this.allergies = allergies;
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

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Dosage.Frequency[] getFrequency() {
        return frequency;
    }

    public String getAllergies() {
        return allergies;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
