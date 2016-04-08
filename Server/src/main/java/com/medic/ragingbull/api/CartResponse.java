/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartResponse {


    @JsonProperty
    private String userId;

    @JsonProperty
    private List<CartItemResponse> items;

    public CartResponse(String userId, List<CartItemResponse> items) {
        this.userId = userId;
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }
}
