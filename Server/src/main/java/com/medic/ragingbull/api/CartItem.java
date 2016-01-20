/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class CartItem {
    private String id;
    private String name;
    private String entityType;
    private Integer quantity;

    public CartItem(String id, String name, String entityType, Integer quantity) {
        this.id = id;
        this.name = name;
        this.entityType = entityType;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEntityType() {
        return entityType;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
