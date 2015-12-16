/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.LocationSpeciality;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;


/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PractitionerLocation extends EntityLocation{

    @NotNull(message = ValidationConstants.DISCIPLINE_MSG_EMPTY)
    @JsonProperty
    public LocationSpeciality speciality;

    public PractitionerLocation() {}

    public PractitionerLocation(String name, String description, LocationSpeciality speciality, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, String licenseDoc) {
        this.name = name;
        this.description = description;
        this.speciality = speciality;
        this.location = location;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.landmark = landmark;
        this.longitude = longitude;
        this.latitude = latitude;
        this.workingHours = workingHours;
        this.workingDays = workingDays;
        this.license = licenseDoc;
    }

    public PractitionerLocation(String id, String entityId, String userId, String name, String description, LocationSpeciality speciality, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive, String licenseDoc, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.entityId = entityId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.speciality = speciality;
        this.location = location;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.landmark = landmark;
        this.longitude = longitude;
        this.latitude = latitude;
        this.workingHours = workingHours;
        this.workingDays = workingDays;
        this.isVerified = isVerified;
        this.isActive = isActive;
        this.license = licenseDoc;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocationSpeciality getSpeciality() {
        return speciality;
    }
}
