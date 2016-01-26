/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.api.CartResponse;
import com.medic.ragingbull.api.OrderResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.jdbi.dao.CartDao;
import com.medic.ragingbull.jdbi.dao.CartItemsDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */

public class CartService {

    private CartDao cartDao;
    private CartItemsDao cartItemsDao;
    private TransactionalDao transactionalDao;

    @Inject
    public CartService(CartDao cartDao, CartItemsDao cartItemsDao, TransactionalDao transactionalDao) {
        this.cartDao = cartDao;
        this.cartItemsDao = cartItemsDao;
        this.transactionalDao = transactionalDao;
    }

    public CartResponse addItemToCart(Session session, String userId, List<CartItem> items) {
        return null;
    }

    public List<CartResponse> getUserCart(Session session, String userId) {
        return null;
    }

    public boolean removeCartItem(Session session, String userId) {
        return false;
    }

    public boolean emptyUserCart(Session session, String userId) {
        return false;
    }

    public OrderResponse orderCart(Session session, String userId) {
        return null;
    }
}
