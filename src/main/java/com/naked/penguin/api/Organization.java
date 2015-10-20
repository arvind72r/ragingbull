/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {
    private String id;
    @NotEmpty
    @JsonProperty
    private String name;
    @NotEmpty
    @JsonProperty
    private String type;
    private DateTime validity;

    public Organization() {
        // Constructor for deserialization
    }

    public Organization(String id, String name, String type, DateTime validity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.validity = validity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateTime getValidity() {
        return validity;
    }

    public void setValidity(DateTime validity) {
        this.validity = validity;
    }
}
