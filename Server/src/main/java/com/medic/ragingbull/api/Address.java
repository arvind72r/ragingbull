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

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @JsonProperty
    private String id;

    @JsonProperty
    private String userId;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String label;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String address1;

    @JsonProperty
    private String address2;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String city;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String state;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private String landmark;

    @JsonProperty
    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private Integer zip;

    public Address() {}

    public Address(String id, String userId, String label, String address1, String address2, String city, String state, String landmark, Integer zip) {
        this.id = id;
        this.userId = userId;
        this.label = label;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.landmark = landmark;
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLabel() {
        return label;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Integer getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getLandmark() {
        return landmark;
    }
}
