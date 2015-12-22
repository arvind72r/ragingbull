/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

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
public class Member {

    @JsonProperty
    private String id;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotEmpty(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String name;

    @Length(max = ValidationConstants.EMAIL_MAX,
            min = ValidationConstants.EMAIL_MIN,
            message = ValidationConstants.EMAIL_MSG_SIZE)
    @Email(message = ValidationConstants.EMAIL_MSG_INVALID)
    @JsonProperty
    private String email;

    @Length(max = ValidationConstants.PHONE_MAX,
            min = ValidationConstants.PHONE_MIN,
            message = ValidationConstants.PHONE_MSG_SIZE)
    @JsonProperty
    private String phone;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private SystemConstants.Sex sex;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private DateTime dob;

    public Member() {};

    public Member(String name, String email, String phone, SystemConstants.Sex sex, DateTime dob) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.dob = dob;
    }

    public Member(String id, String name, String email, String phone, SystemConstants.Sex sex, DateTime dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.dob = dob;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SystemConstants.Sex getSex() {
        return sex;
    }

    public void setSex(SystemConstants.Sex sex) {
        this.sex = sex;
    }

    public DateTime getDob() {
        return dob;
    }

    public void setDob(DateTime dob) {
        this.dob = dob;
    }
}
