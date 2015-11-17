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
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consultation {

    @JsonProperty
    private String id;

    @JsonProperty
    private String locationId;

    @JsonProperty
    private String userId;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotBlank(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String consultee;

    @Length(max = ValidationConstants.NAME_MAX,
            min = ValidationConstants.NAME_MIN,
            message = ValidationConstants.NAME_MSG_SIZE)
    @NotBlank(message = ValidationConstants.NAME_MSG_EMPTY)
    @JsonProperty
    private String practitionerName;

    @JsonProperty
    private List<String> remarks;

    @JsonProperty
    private Boolean active;

    private DateTime createdAt;
    private DateTime updatedAt;


}
