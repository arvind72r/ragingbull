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
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favourite {

    @JsonProperty
    private String id;

    @JsonProperty
    private String userId;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String label;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String entityType;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String entityId;

    public Favourite() {};
    public Favourite(String id, String userId, String label, String entityType, String entityId) {
        this.id = id;
        this.userId = userId;
        this.label = label;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLabel() {
        return label;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }
}
