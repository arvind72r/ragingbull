/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationResponse {

    private String name;
    private String type;
    private DateTime validity;

    public OrganizationResponse() {

    }
    public OrganizationResponse(Organization org) {
        this.name = org.getName();
        this.type = org.getType();
        this.validity = org.getValidity();
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
