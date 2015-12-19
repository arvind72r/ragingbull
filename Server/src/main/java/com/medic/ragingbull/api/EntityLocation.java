/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class EntityLocation {

    @JsonProperty
    public String id;

    @JsonProperty
    public String entityId;

    @JsonProperty
    public String userId;

    @NotBlank(message = ValidationConstants.NAME_MSG_EMPTY)
    public String name;

    @Length(min = ValidationConstants.DESCRIPTION_MIN,
            max = ValidationConstants.DESCRIPTION_MAX,
            message = ValidationConstants.DESCRIPTION_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DESCRIPTION_MSG_EMPTY)
    @JsonProperty
    public String description;

    @Length(max = ValidationConstants.LOCATION_NAME_MAX,
            min = ValidationConstants.LOCATION_NAME_MIN,
            message = ValidationConstants.LOCATION_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LOCATION_NAME_MSG_EMPTY)
    @JsonProperty
    public String location;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    public String primaryContact;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @JsonProperty
    public String secondaryContact;

    @Length(min = ValidationConstants.ADDRESS1_MIN,
            max = ValidationConstants.ADDRESS1_MAX,
            message = ValidationConstants.ADDRESS1_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ADDRESS1_MSG_EMPTY)
    @JsonProperty
    public String address1;

    @Length(min = ValidationConstants.ADDRESS2_MIN,
            max = ValidationConstants.ADDRESS2_MAX,
            message = ValidationConstants.ADDRESS2_MSG_SIZE)
    @JsonProperty
    public String address2;

    @Length(min = ValidationConstants.CITY_MIN,
            max = ValidationConstants.CITY_MAX,
            message = ValidationConstants.CITY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.CITY_MSG_EMPTY)
    @JsonProperty
    public String city;

    @Length(min = ValidationConstants.STATE_MIN,
            max = ValidationConstants.STATE_MAX,
            message = ValidationConstants.STATE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.STATE_MSG_EMPTY)
    @JsonProperty
    public String state;

    @JsonProperty
    public Long zip;

    @Length(max = ValidationConstants.COUNTRY_MAX,
            min = ValidationConstants.CONTACT_NAME_MIN,
            message = ValidationConstants.COUNTRY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.COUNTRY_MSG_EMPTY)
    @JsonProperty
    public String country;

    @Length(max = ValidationConstants.LANDMARK_MAX,
            min = ValidationConstants.LANDMARK_MIN,
            message = ValidationConstants.LANDMARK_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LANDMARK_MSG_EMPTY)
    @JsonProperty
    public String landmark;

    @JsonProperty
    public Float longitude;

    @JsonProperty
    public Float latitude;

    @JsonProperty
    public Integer workingHours;

    @JsonProperty
    public Integer workingDays;

    @JsonProperty
    public Boolean isVerified;

    @JsonProperty
    public Boolean isActive;

    @JsonProperty
    public String license;

    @JsonProperty
    public DateTime createdAt;

    @JsonProperty
    public DateTime updatedAt;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public String getSecondaryContact() {
        return secondaryContact;
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

    public Long getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getLandmark() {
        return landmark;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Boolean getIsActive() {
        return isActive;
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
