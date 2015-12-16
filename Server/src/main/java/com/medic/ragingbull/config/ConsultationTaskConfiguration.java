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
public class ConsultationTaskConfiguration extends Configuration{

    @NotNull
    @JsonProperty
    private Integer count;

    public Integer getCount() {
        return count;
    }
}
