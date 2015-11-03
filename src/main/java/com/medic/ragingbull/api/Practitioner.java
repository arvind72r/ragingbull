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
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Practitioner {

    @JsonProperty
    private String id;


    @JsonProperty
    private String userId;

    @Length(min = ValidationConstants.DESCRIPTION_MIN,
            max = ValidationConstants.DESCRIPTION_MAX,
            message = ValidationConstants.DESCRIPTION_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DESCRIPTION_MSG_EMPTY)
    @JsonProperty
    private String description;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String primaryContact;

    @JsonProperty
    private String secondaryContact;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @JsonProperty
    private String primaryId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @JsonProperty
    private String secondaryId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ID_MSG_EMPTY)
    @JsonProperty
    private String registrationId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ID_MSG_EMPTY)
    @JsonProperty
    private String registrationAuthority;

    @JsonProperty
    private String license;

    @JsonProperty
    private Boolean verified;

    @JsonProperty
    private Boolean active;

    @JsonProperty
    private DateTime createdAt;

    @JsonProperty
    private DateTime updatedAt;

    public Practitioner() {}

    public Practitioner(String id, String userId, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license, Boolean verified, Boolean active) {
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
        this.verified = verified;
        this.active = active;
    }

    public Practitioner(String id, String userId, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license, Boolean verified, Boolean active, DateTime createdAt, DateTime updatedAt) {
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
        this.verified = verified;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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

    public Boolean getVerified() {
        return verified;
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
}
