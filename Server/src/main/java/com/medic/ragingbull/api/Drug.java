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
import java.util.ArrayList;
import java.util.List;

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

    @Length(min = ValidationConstants.DRUG_ALLERGY_MIN,
            max = ValidationConstants.DRUG_ALLERGY_MAX,
            message = ValidationConstants.DRUG_ALLERGY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUG_ALLERGY_MSG_EMPTY)
    @JsonProperty
    private String allergies;

    @JsonIgnore
    private Long frequency = 0L;

    @Size(  max = ValidationConstants.DRUG_FREQUENCY_MAX,
            min = ValidationConstants.DRUG_FREQUENCY_MIN,
            message = ValidationConstants.DRUG_FREQUENCY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DRUG_FREQUENCY_MSG_EMPTY)
    @JsonProperty
    private List<Dosage.Regularity> regularity;


    @JsonProperty
    private Boolean active;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private DateTime updatedAt;

    public Drug() {};

    public Drug(String id, String consultationId, String practitionerId, String userId, String name, String manufacturer, Integer quantity, String allergies, Long frequency, Boolean active, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.consultationId = consultationId;
        this.practitionerId = practitionerId;
        this.userId = userId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.allergies = allergies;
        this.frequency = frequency;
        this.regularity = new ArrayList<>();
        for (Dosage.Regularity regularity : Dosage.Regularity.values()) {
            if ((frequency & regularity.getBitValue()) == regularity.getBitValue()) {
                this.regularity.add(regularity);
            }
        }
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

    public List<Dosage.Regularity> getRegularity() {
        return regularity;
    }

    public void setRegularity(List<Dosage.Regularity> regularity) {
        this.regularity = regularity;
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

    public Long getFrequency() {
        if (frequency == 0) {
            for(Dosage.Regularity regularity1 : this.regularity) {
                frequency = frequency | regularity1.getBitValue();
            }
        }
        return frequency;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}