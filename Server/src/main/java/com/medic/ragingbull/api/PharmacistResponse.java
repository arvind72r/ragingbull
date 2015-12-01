/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vamshi Molleti
 */
public class PharmacistResponse extends AbstractResponse{

    @JsonProperty
    private String id;

    @JsonProperty
    private String description;

    @JsonProperty
    private String primaryContact;

    @JsonProperty
    private String secondaryContact;

    @JsonProperty
    private String primaryId;

    @JsonProperty
    private String secondaryId;

    @JsonProperty
    private String registrationId;

    @JsonProperty
    private String registrationAuthority;

    @JsonProperty
    private String license;

    public PharmacistResponse(String id, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license) {
        this.id = id;
        this.description = description;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.registrationId = registrationId;
        this.registrationAuthority = registrationAuthority;
        this.license = license;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public String getSecondaryId() {
        return secondaryId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public String getLicense() {
        return license;
    }
}
