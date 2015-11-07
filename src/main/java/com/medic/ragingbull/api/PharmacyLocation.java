/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyLocation extends EntityLocation{

    public PharmacyLocation() {}

    public PharmacyLocation(String entityId, String userId, String name, String description, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive, String licenseDoc, DateTime createdAt, DateTime updatedAt) {
        this.entityId = entityId;
        this.userId = userId;
        this.name = name;
        this.description = description;
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

    public PharmacyLocation(String id, String entityId, String userId, String name, String description, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive, String licenseDoc, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.entityId = entityId;
        this.userId = userId;
        this.name = name;
        this.description = description;
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
}
