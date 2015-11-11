/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Vamshi Molleti
 */
public class ImagesConfiguration extends Configuration{

    @NotEmpty
    @JsonProperty
    private String path;

    @NotEmpty
    @JsonProperty
    private String supportedTypes;

    @NotEmpty
    @JsonProperty
    private Integer size;


    public String getPath() {
        return path;
    }

    public String getSupportedTypes() {
        return supportedTypes;
    }

    public Integer getSize() {
        return size;
    }
}
