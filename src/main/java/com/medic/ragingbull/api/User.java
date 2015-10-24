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
import com.medic.ragingbull.config.ValidationConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

    @JsonProperty
    private Boolean verified;

    @JsonProperty
    private Boolean isNative;

    @JsonIgnore
    private DateTime updatedAt;

    @JsonIgnore
    private DateTime createdAt;

    @JsonIgnore
    private String hash;

    public User() {
        // For Deserialization
    }

    public User(String id, String name, String password, String email, Boolean verified, Boolean isNative, DateTime updatedAt, DateTime createdAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.verified = verified;
        this.isNative = isNative;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public User(String id, String email, String name, Boolean isVerified, Boolean isNative, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.verified = isVerified;
        this.isNative = isNative;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public User(String id, String name, String email, Boolean verified, Boolean isNative, DateTime updatedAt, DateTime createdAt, String hash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.verified = verified;
        this.isNative = isNative;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.hash = hash;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return !(email != null ? !email.equals(user.email) : user.email != null) && !(id != null ? !id.equals(user.id) : user.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getIsNative() {
        return isNative;
    }

    public void setIsNative(Boolean isNative) {
        this.isNative = isNative;
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
}