/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

/**
 * Created by Vamshi Molleti
 */
public class MemberTaskConfiguration extends Configuration {
    @NotNull
    @JsonProperty
    private Integer count;

    @NotNull
    @JsonProperty
    private String prefix;

    @NotNull
    @JsonProperty
    private String emailDomain;

    @NotNull
    @JsonProperty
    private String phonePrefix;

    @NotNull
    @JsonProperty
    private String password;

    @NotNull
    @JsonProperty
    private String sex;

    @NotNull
    @JsonProperty
    private String dob;

    public Integer getCount() {
        return count;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getDob() {
        return dob;
    }
}
