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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drug {

    @Length(min = ValidationConstants.NAME_MIN,
            max = ValidationConstants.NAME_MAX,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @Min(ValidationConstants.DRUG_QUANTITY_MIN)
    @NotEmpty(message = ValidationConstants.DRUG_QUANTITY_MSG_EMPTY)
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

    private Integer frequencyBit;

    public Drug(String allergies, Dosage.Frequency[] frequency, Integer quantity, String name) {
        this.allergies = allergies;
        this.frequency = frequency;
        this.quantity = quantity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Dosage.Frequency[] getFrequency() {
        return frequency;
    }

    public void setFrequency(Dosage.Frequency[] frequency) {
        this.frequency = frequency;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public Integer getFrequencyBit() {
        frequencyBit = 0;
        for(Dosage.Frequency frequency : this.frequency) {
            frequencyBit = frequencyBit & frequency.getBitValue();
        }
        return frequencyBit;
    }
}
