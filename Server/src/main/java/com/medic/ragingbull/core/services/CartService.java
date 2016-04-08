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
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.CartDao;
import com.medic.ragingbull.jdbi.dao.CartItemsDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */

public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

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
        try{
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(String.format("Adding items to user cart for user %s", userId));

                int[] itemsAdded = cartDao.addItem(userId, items);

                if (itemsAdded.length != items.size()) {
                    LOGGER.error(String.format("Error adding cart items for the user %s. DB failed to save the items", userId));
                    throw new StorageException("Error registering user. Please try again");
                }


            }
        } catch (Exception e) {
        }
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
