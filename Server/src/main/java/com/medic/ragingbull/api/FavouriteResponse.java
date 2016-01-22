/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vamshi Molleti
 */
public class FavouriteResponse {

    @JsonProperty
    private String id;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String label;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String entity_type;

    @NotBlank(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String entity_id;

    public FavouriteResponse(String id, String label, String entity_type, String entity_id) {
        this.id = id;
        this.label = label;
        this.entity_type = entity_type;
        this.entity_id = entity_id;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public String getEntity_id() {
        return entity_id;
    }
}
