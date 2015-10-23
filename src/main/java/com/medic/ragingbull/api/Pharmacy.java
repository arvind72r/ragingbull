/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.config.ValidationConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pharmacy {

    @JsonProperty
    private String id;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @Length(max = ValidationConstants.CONTACT_NAME_MAX,
            min = ValidationConstants.CONTACT_NAME_MIN,
            message = ValidationConstants.CONTACT_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.CONTACT_NAME_MSG_EMPTY)
    @JsonProperty
    private String contactName;

    @Length(max = ValidationConstants.LOCATION_NAME_MAX,
            min = ValidationConstants.LOCATION_NAME_MIN,
            message = ValidationConstants.LOCATION_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LOCATION_NAME_MSG_EMPTY)
    @JsonProperty
    private String location;

    @Length(max = ValidationConstants.PHONE_MIN,
            min = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private Integer primaryContact;

    @JsonProperty
    private Integer secondaryContact;

    @Length(max = ValidationConstants.ADDRESS1_MIN,
            min = ValidationConstants.ADDRESS1_MAX,
            message = ValidationConstants.ADDRESS1_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String address1;

    @Length(max = ValidationConstants.ADDRESS2_MIN,
            min = ValidationConstants.ADDRESS2_MAX,
            message = ValidationConstants.ADDRESS2_MSG_SIZE)
    @JsonProperty
    private String address2;

    @Length(max = ValidationConstants.CITY_MIN,
            min = ValidationConstants.CITY_MAX,
            message = ValidationConstants.CITY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.CITY_MSG_EMPTY)
    @JsonProperty
    private String city;

    @Length(max = ValidationConstants.STATE_MIN,
            min = ValidationConstants.STATE_MAX,
            message = ValidationConstants.STATE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.STATE_MSG_EMPTY)
    @JsonProperty
    private String state;

    @Length(max = ValidationConstants.ZIP_MIN,
            min = ValidationConstants.ZIP_MIN,
            message = ValidationConstants.ZIP_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ZIP_MSG_EMPTY)
    @JsonProperty
    private Long zip;

    @Length(max = ValidationConstants.COUNTRY_MIN,
            min = ValidationConstants.CONTACT_NAME_MAX,
            message = ValidationConstants.COUNTRY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.COUNTRY_MSG_EMPTY)
    @JsonProperty
    private String country;

    @Length(max = ValidationConstants.LANDMARK_MIN,
            min = ValidationConstants.LANDMARK_MAX,
            message = ValidationConstants.LANDMARK_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LANDMARK_MSG_EMPTY)
    @JsonProperty
    private String landmark;

    @JsonProperty
    private Float longitude;

    @JsonProperty
    private Float latitude;

    @NotEmpty(message = ValidationConstants.DELIVERY_RADIUS_MSG_EMPTY)
    @JsonProperty
    private Integer deliveryRadius;

    @NotEmpty(message = ValidationConstants.WORKING_HOURS_MSG_EMPTY)
    @JsonProperty
    private Integer workingHours;

    @NotEmpty(message = ValidationConstants.WORKING_DAYS_MSG_EMPTY)
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

    public Pharmacy(String name, String contactName, String location, Integer primaryContact, Integer secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer deliveryRadius, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive, String licenseDoc, DateTime createdAt, DateTime updatedAt) {
        this.name = name;
        this.contactName = contactName;
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
        this.deliveryRadius = deliveryRadius;
        this.workingHours = workingHours;
        this.workingDays = workingDays;
        this.isVerified = isVerified;
        this.isActive = isActive;
        this.licenseDoc = licenseDoc;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(Integer primaryContact) {
        this.primaryContact = primaryContact;
    }

    public Integer getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(Integer secondaryContact) {
        this.secondaryContact = secondaryContact;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(Integer deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLicenseDoc() {
        return licenseDoc;
    }

    public void setLicenseDoc(String licenseDoc) {
        this.licenseDoc = licenseDoc;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
