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
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;


/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PractitionerLocation {

    @JsonProperty
    private String id;

    @JsonProperty
    private String practitionerId;


    @NotBlank(message = ValidationConstants.NAME_MSG_EMPTY)
    private String name;

    @Length(max = ValidationConstants.CONTACT_NAME_MAX,
            min = ValidationConstants.CONTACT_NAME_MIN,
            message = ValidationConstants.CONTACT_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.CONTACT_NAME_MSG_EMPTY)
    @JsonProperty
    private String contactName;

    @Length(min = ValidationConstants.DESCRIPTION_MIN,
            max = ValidationConstants.DESCRIPTION_MAX,
            message = ValidationConstants.DESCRIPTION_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DESCRIPTION_MSG_EMPTY)
    @JsonProperty
    private String description;

    @Length(min = ValidationConstants.DISCIPLINE_MIN,
            max = ValidationConstants.DISCIPLINE_MAX,
            message = ValidationConstants.DISCIPLINE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.DISCIPLINE_MSG_EMPTY)
    @JsonProperty
    private String discipline;

    @Length(max = ValidationConstants.LOCATION_NAME_MAX,
            min = ValidationConstants.LOCATION_NAME_MIN,
            message = ValidationConstants.LOCATION_NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LOCATION_NAME_MSG_EMPTY)
    @JsonProperty
    private String location;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String primaryContact;

    @Length(min = ValidationConstants.PHONE_MIN,
            max = ValidationConstants.PHONE_MAX,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @JsonProperty
    private String secondaryContact;

    @Length(min = ValidationConstants.ADDRESS1_MIN,
            max = ValidationConstants.ADDRESS1_MAX,
            message = ValidationConstants.ADDRESS1_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.ADDRESS1_MSG_EMPTY)
    @JsonProperty
    private String address1;

    @Length(min = ValidationConstants.ADDRESS2_MIN,
            max = ValidationConstants.ADDRESS2_MAX,
            message = ValidationConstants.ADDRESS2_MSG_SIZE)
    @JsonProperty
    private String address2;

    @Length(min = ValidationConstants.CITY_MIN,
            max = ValidationConstants.CITY_MAX,
            message = ValidationConstants.CITY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.CITY_MSG_EMPTY)
    @JsonProperty
    private String city;

    @Length(min = ValidationConstants.STATE_MIN,
            max = ValidationConstants.STATE_MAX,
            message = ValidationConstants.STATE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.STATE_MSG_EMPTY)
    @JsonProperty
    private String state;


    @JsonProperty
    private Long zip;

    @Length(max = ValidationConstants.COUNTRY_MAX,
            min = ValidationConstants.CONTACT_NAME_MIN,
            message = ValidationConstants.COUNTRY_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.COUNTRY_MSG_EMPTY)
    @JsonProperty
    private String country;

    @Length(max = ValidationConstants.LANDMARK_MAX,
            min = ValidationConstants.LANDMARK_MIN,
            message = ValidationConstants.LANDMARK_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.LANDMARK_MSG_EMPTY)
    @JsonProperty
    private String landmark;

    @JsonProperty
    private Float longitude;

    @JsonProperty
    private Float latitude;

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

    public PractitionerLocation() {}

    public PractitionerLocation(String id, String practitionerId, String name, String contactName, String description, String discipline, String location, String primaryContact, String secondaryContact, String address1, String address2, String city, String state, Long zip, String country, String landmark, Float longitude, Float latitude, Integer workingHours, Integer workingDays, Boolean isVerified, Boolean isActive, String licenseDoc, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.name = name;
        this.contactName = contactName;
        this.description = description;
        this.discipline = discipline;
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

    public String getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(String secondaryContact) {
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
