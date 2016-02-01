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
    private String entityRefType;
    private String entityRefId;
    private Integer quantity;

    public CartItem(String id, String name, String entityRefType, String entityRefId, Integer quantity) {
        this.id = id;
        this.name = name;
        this.entityRefType = entityRefType;
        this.entityRefId = entityRefId;
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEntityRefType() {
        return entityRefType;
    }

    public String getEntityRefId() {
        return entityRefId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
