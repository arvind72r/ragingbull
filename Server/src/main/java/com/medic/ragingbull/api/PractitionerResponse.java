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

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PractitionerResponse extends AbstractResponse{

    @JsonProperty
    private String id;

    @JsonIgnore
    private String userId;

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

    public PractitionerResponse(){};

    public PractitionerResponse(String id, String userId, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license) {
        this.id = id;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PractitionerResponse response = (PractitionerResponse) o;

        if (id != null ? !id.equals(response.id) : response.id != null) return false;
        if (description != null ? !description.equals(response.description) : response.description != null)
            return false;
        if (primaryContact != null ? !primaryContact.equals(response.primaryContact) : response.primaryContact != null)
            return false;
        if (secondaryContact != null ? !secondaryContact.equals(response.secondaryContact) : response.secondaryContact != null)
            return false;
        if (primaryId != null ? !primaryId.equals(response.primaryId) : response.primaryId != null) return false;
        if (secondaryId != null ? !secondaryId.equals(response.secondaryId) : response.secondaryId != null)
            return false;
        if (registrationId != null ? !registrationId.equals(response.registrationId) : response.registrationId != null)
            return false;
        if (registrationAuthority != null ? !registrationAuthority.equals(response.registrationAuthority) : response.registrationAuthority != null)
            return false;
        return !(license != null ? !license.equals(response.license) : response.license != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (primaryContact != null ? primaryContact.hashCode() : 0);
        result = 31 * result + (secondaryContact != null ? secondaryContact.hashCode() : 0);
        result = 31 * result + (primaryId != null ? primaryId.hashCode() : 0);
        result = 31 * result + (secondaryId != null ? secondaryId.hashCode() : 0);
        result = 31 * result + (registrationId != null ? registrationId.hashCode() : 0);
        result = 31 * result + (registrationAuthority != null ? registrationAuthority.hashCode() : 0);
        result = 31 * result + (license != null ? license.hashCode() : 0);
        return result;
    }
}
