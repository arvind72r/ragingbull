/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyLocationResponse extends AbstractResponse{

    private String id;
    private String userId;
    private String practitionerId;
    private String name;
    private String description;
    private String location;
    private String primaryContact;
    private String secondaryContact;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private Long zip;
    private String country;
    private String landmark;
    private Float longitude;
    private Float latitude;
    private Integer workingHours;
    private Integer workingDays;
    private String license;
    private Boolean isVerified;
    private Boolean isActive;

    public PharmacyLocationResponse(String id, String userId, String practitionerId, String name, String description, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, String license, Boolean isVerified, Boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.practitionerId = practitionerId;
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
        this.license = license;
        this.isVerified = isVerified;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPractitionerId() {
        return practitionerId;
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

    public String getLicense() {
        return license;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
