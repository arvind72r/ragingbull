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
public class CompanyResponse {
    private String name;
    private String displayName;
    private String intro;
    private DateTime createdAt;

    public CompanyResponse() {};

    public CompanyResponse(final Company company) {
        this.name = company.getName();
        this.displayName = company.getDisplayName();
        this.intro = company.getIntro();
        this.createdAt = company.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIntro() {
        return intro;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }
}
