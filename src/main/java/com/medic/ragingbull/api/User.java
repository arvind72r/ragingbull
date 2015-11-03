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
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

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

    @Length(max = ValidationConstants.EMAIL_MAX,
            min = ValidationConstants.EMAIL_MIN,
            message = ValidationConstants.EMAIL_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.EMAIL_MSG_EMPTY)
    @Email(message = ValidationConstants.EMAIL_MSG_INVALID)
    @JsonProperty
    private String email;

    @Length(max = ValidationConstants.PHONE_MAX,
            min = ValidationConstants.PHONE_MIN,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String contactNo;

    @Length(max = ValidationConstants.INLET_TYPE_MAX,
            min = ValidationConstants.INLET_TYPE_MIN,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.PHONE_MSG_EMPTY)
    @JsonProperty
    private String inletType;

    @JsonProperty
    private Boolean verified;

    @JsonProperty
    private Boolean active;

    @JsonProperty
    private String pictureUrl;

    @JsonIgnore
    private Integer role;

    @JsonIgnore
    private Integer category;

    @JsonIgnore
    private DateTime updatedAt;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private String hash;

    public User() {
        // For Deserialization
    }

    public User(String id, String name, String password, String email, String contactNo, String inletType, Boolean verified, Boolean active, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.inletType = inletType;
        this.verified = verified;
        this.active = active;
        this.pictureUrl = pictureUrl;
    }

    public User(String id, String name, String hash, String email, String contactNo, String inletType, Boolean verified, Boolean active, String pictureUrl, Integer role, Integer category, DateTime updatedAt, DateTime createdAt) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.email = email;
        this.contactNo = contactNo;
        this.inletType = inletType;
        this.verified = verified;
        this.active = active;
        this.pictureUrl = pictureUrl;
        this.role = role;
        this.category = category;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getInletType() {
        return inletType;
    }

    public void setInletType(String inletType) {
        this.inletType = inletType;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}