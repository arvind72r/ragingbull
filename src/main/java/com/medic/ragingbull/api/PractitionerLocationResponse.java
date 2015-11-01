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
public class PractitionerLocationResponse extends AbstractResponse{

    private String id;
    private String practitionerId;
    private String name;
    private String contactName;
    private String description;
    private String discipline;
    private String location;
    private String primaryContact;
    private String landmark;
    private Float longitude;
    private Float latitude;
    private Integer workingHours;
    private Integer workingDays;
    private Boolean isVerified;
    private Boolean isActive;

    public PractitionerLocationResponse(String id, String practitionerId, String name, String contactName, String description, String discipline, String location, String primaryContact, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.name = name;
        this.contactName = contactName;
        this.description = description;
        this.discipline = discipline;
        this.location = location;
        this.primaryContact = primaryContact;
        this.landmark = landmark;
        this.longitude = longitude;
        this.latitude = latitude;
        this.workingHours = workingHours;
        this.workingDays = workingDays;
        this.isVerified = isVerified;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getName() {
        return name;
    }

    public String getContactName() {
        return contactName;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getLocation() {
        return location;
    }

    public String getPrimaryContact() {
        return primaryContact;
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
}
