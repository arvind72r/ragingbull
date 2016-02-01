/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemResponse {
    @JsonProperty
    private String id;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer quantity;

    @JsonProperty
    private String entityRefId;

    @JsonProperty
    private String entityRefType;

    public CartItemResponse(String id, String userId, String name, Integer quantity, String entityRefId, String entityRefType) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.quantity = quantity;
        this.entityRefId = entityRefId;
        this.entityRefType = entityRefType;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getEntityRefId() {
        return entityRefId;
    }

    public String getEntityRefType() {
        return entityRefType;
    }
}
