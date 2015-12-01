/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class Entity {

    @JsonProperty
    protected String id;


    @JsonIgnore
    protected String userId;

    @Length(min = ValidationConstants.DESCRIPTION_MIN,
            max = ValidationConstants.DESCRIPTION_MAX,
            message = ValidationConstants.DESCRIPTION_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DESCRIPTION_MSG_EMPTY)
    @JsonProperty
    protected String description;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    protected String primaryContact;

    @JsonProperty
    protected String secondaryContact;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @JsonProperty
    protected String primaryId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @JsonProperty
    protected String secondaryId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ID_MSG_EMPTY)
    @JsonProperty
    protected String registrationId;

    @Length(min = ValidationConstants.ID_MIN,
            max = ValidationConstants.ID_MAX,
            message = ValidationConstants.ID_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ID_MSG_EMPTY)
    @JsonProperty
    protected String registrationAuthority;

    @JsonProperty
    protected String license;

    @JsonIgnore
    protected DateTime createdAt;

    @JsonIgnore
    protected DateTime updatedAt;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}
