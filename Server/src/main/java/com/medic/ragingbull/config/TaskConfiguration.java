/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Vamshi Molleti
 */
public class TaskConfiguration extends Configuration{

    @Valid
    @NotNull
    @JsonProperty("enabled")
    Boolean enabled;
    @Valid
    @NotNull
    @JsonProperty("user")
    UserTaskConfiguration userTaskConfiguration = new UserTaskConfiguration();

    @Valid
    @NotNull
    @JsonProperty("practitioner")
    PractitionerTaskConfiguration practitionerTaskConfiguration = new PractitionerTaskConfiguration();

    @Valid
    @NotNull
    @JsonProperty("practitionerLocations")
    PractitionerLocationTaskConfiguration practitionerLocationTaskConfiguration = new PractitionerLocationTaskConfiguration();

    @Valid
    @NotNull
    @JsonProperty("pharmacist")
    PharmacistTaskConfiguration pharmacistTaskConfiguration = new PharmacistTaskConfiguration();

    @Valid
    @NotNull
    @JsonProperty("pharmacies")
    PharmacyTaskConfiguration pharmacyTaskConfiguration = new PharmacyTaskConfiguration();

    @Valid
    @NotNull
    @JsonProperty("consultations")
    ConsultationTaskConfiguration consultationTaskConfiguration = new ConsultationTaskConfiguration();

    public Boolean getEnabled() {
        return enabled;
    }

    public UserTaskConfiguration getUserTaskConfiguration() {
        return userTaskConfiguration;
    }

    public PractitionerTaskConfiguration getPractitionerTaskConfiguration() {
        return practitionerTaskConfiguration;
    }

    public PractitionerLocationTaskConfiguration getPractitionerLocationTaskConfiguration() {
        return practitionerLocationTaskConfiguration;
    }

    public PharmacistTaskConfiguration getPharmacistTaskConfiguration() {
        return pharmacistTaskConfiguration;
    }

    public PharmacyTaskConfiguration getPharmacyTaskConfiguration() {
        return pharmacyTaskConfiguration;
    }

    public ConsultationTaskConfiguration getConsultationTaskConfiguration() {
        return consultationTaskConfiguration;
    }
}
