/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String label;

    @JsonProperty
    private String address1;

    @JsonProperty
    private String address2;

    @JsonProperty
    private String city;

    @JsonProperty
    private String state;

    @JsonProperty
    private String landmark;

    @JsonProperty
    private Integer zip;


    public AddressResponse(String id, String userId, String label, String address1, String address2, String city, String state, String landmark, Integer zip) {
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

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getLandmark() {
        return landmark;
    }

    public Integer getZip() {
        return zip;
    }
}
