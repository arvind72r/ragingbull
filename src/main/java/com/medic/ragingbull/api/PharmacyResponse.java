/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public  class PharmacyResponse extends AbstractResponse{

    // HTTP status



    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String contactName;

    @JsonProperty
    private String location;

    @JsonProperty
    private Integer primaryContact;

    @JsonProperty
    private Integer secondaryContact;

    @JsonProperty
    private String address1;

    @JsonProperty
    private String address2;

    @JsonProperty
    private String city;

    @JsonProperty
    private String state;

    @JsonProperty
    private Long zip;

    @JsonProperty
    private String country;

    @JsonProperty
    private String landmark;

    @JsonProperty
    private Float longitude;

    @JsonProperty
    private Float latitude;

    @JsonProperty
    private Integer deliveryRadius;

    @JsonProperty
    private Integer workingHours;

    @JsonProperty
    private Integer workingDays;

    @JsonProperty
    private Boolean isVerified;

    @JsonProperty
    private Boolean isActive;

    @JsonProperty
    private String licenseDoc;

    @JsonProperty
    private DateTime createdAt;

    @JsonProperty
    private DateTime updatedAt;

    public PharmacyResponse() {};

    public PharmacyResponse(PharmacyBack pharmacyBack, Boolean includeDetails) {

        this.id = pharmacyBack.getId();
        this.name = pharmacyBack.getName();
        this.location = pharmacyBack.getLocation();
        this.landmark = pharmacyBack.getLandmark();
        this.primaryContact = pharmacyBack.getPrimaryContact();
        this.deliveryRadius = pharmacyBack.getDeliveryRadius();
        this.workingHours = pharmacyBack.getWorkingHours();
        this.workingDays = pharmacyBack.getWorkingDays();
        this.isVerified = pharmacyBack.getIsVerified();
        this.isActive = pharmacyBack.getIsActive();
        this.createdAt = pharmacyBack.getCreatedAt();
        this.updatedAt = pharmacyBack.getUpdatedAt();

        if (includeDetails) {
            this.secondaryContact = pharmacyBack.getSecondaryContact();
            this.contactName = pharmacyBack.getContactName();
            this.address1 = pharmacyBack.getAddress1();
            this.address2 = pharmacyBack.getAddress2();
            this.city = pharmacyBack.getCity();
            this.state = pharmacyBack.getState();
            this.zip = pharmacyBack.getZip();
            this.country = pharmacyBack.getCountry();

            this.longitude = pharmacyBack.getLongitude();
            this.latitude = pharmacyBack.getLatitude();
            this.licenseDoc = pharmacyBack.getLicenseDoc();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactName() {
        return contactName;
    }

    public String getLocation() {
        return location;
    }

    public Integer getPrimaryContact() {
        return primaryContact;
    }

    public Integer getSecondaryContact() {
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

    public Integer getDeliveryRadius() {
        return deliveryRadius;
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

    public String getLicenseDoc() {
        return licenseDoc;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}
