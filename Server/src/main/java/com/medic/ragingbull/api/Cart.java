/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class Cart {
    private String pharmacyId;
    private List<CartItem> items;
    private Float totalTax;
    private Float totalPrice;

    public Cart(String pharmacyId, List<CartItem> items, Float totalTax, Float totalPrice) {
        this.pharmacyId = pharmacyId;
        this.items = items;
        this.totalTax = totalTax;
        this.totalPrice = totalPrice;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Float getTotalTax() {
        return totalTax;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }
}
