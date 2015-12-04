/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty
    private String id;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @Length(max = ValidationConstants.PASSWORD_MAX,
            min = ValidationConstants.PASSWORD_MIN,
            message = ValidationConstants.PASSWORD_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PASSWORD_MSG_EMPTY)
    @JsonProperty
    private String password;

    @JsonIgnore
    private String hash;

    @Length(max = ValidationConstants.EMAIL_MAX,
            min = ValidationConstants.EMAIL_MIN,
            message = ValidationConstants.EMAIL_MSG_SIZE)
    @Email(message = ValidationConstants.EMAIL_MSG_INVALID)
    @JsonProperty
    private String email;

    @Length(max = ValidationConstants.PHONE_MAX,
            min = ValidationConstants.PHONE_MIN,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String phone;

    @Length(max = ValidationConstants.INLET_TYPE_MAX,
            min = ValidationConstants.INLET_TYPE_MIN,
            message = ValidationConstants.INLET_TYPE_SIZE)
    @NotEmpty(message = ValidationConstants.INLET_TYPE_EMPTY)
    @JsonProperty
    private String inletType;

    @JsonProperty
    private String pictureUrl;

    @JsonProperty
    private Boolean verified;

    @JsonProperty
    private Boolean active;

    @JsonProperty
    private Practitioner practitioner;

    @JsonProperty
    private Pharmacist pharmacist;

    @JsonIgnore
    private Long role;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private SystemConstants.Sex sex;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private DateTime dob;

    @JsonIgnore
    private DateTime updatedAt;

    @JsonIgnore
    private DateTime createdAt;

    public User() {
        // For Deserialization
    }

    /**
     * Constructor for the JSON Mapper for request
     */
    public User(String name, String password, String email, String phone, String inletType, String pictureUrl, Practitioner practitioner, Pharmacist pharmacist) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.inletType = inletType;
        this.pictureUrl = pictureUrl;
        this.practitioner = practitioner;
        this.pharmacist = pharmacist;
    }

    public User(String id, String name, String hash, String email, String phone, String inletType, String pictureUrl, Boolean verified, Boolean active, Long role, SystemConstants.Sex sex, DateTime dob, DateTime updatedAt, DateTime createdAt) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.email = email;
        this.phone = phone;
        this.inletType = inletType;
        this.pictureUrl = pictureUrl;
        this.verified = verified;
        this.active = active;
        this.role = role;
        this.sex = sex;
        this.dob = dob;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    /**
     * Constructor to be used by DAO Mapper.
     */







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

    public String getPassword() {
        return password;
    }

    public String getHash() {
        return hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInletType() {
        return inletType;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Boolean getActive() {
        return active;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Long getRole() {
        return role;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public SystemConstants.Sex getSex() {
        return sex;
    }

    public DateTime getDob() {
        return dob;
    }
}