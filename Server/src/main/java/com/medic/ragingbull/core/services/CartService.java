/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.CartDao;
import com.medic.ragingbull.jdbi.dao.CartItemsDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */

public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private CartDao cartDao;
    private CartItemsDao cartItemsDao;
    private TransactionalDao transactionalDao;

    @Inject
    public CartService(CartDao cartDao, CartItemsDao cartItemsDao, TransactionalDao transactionalDao) {
        this.cartDao = cartDao;
        this.cartItemsDao = cartItemsDao;
        this.transactionalDao = transactionalDao;
    }

    public CartResponse addItemToCart(Session session, String userId, List<CartItem> items) throws StorageException, ResourceCreationException {
        try {
            List<CartItem> updatedItemList = new ArrayList<>();
            List<CartItemResponse> cartItemsResponse = new ArrayList<>();
            for (CartItem item : items) {
                String id = com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER_CART_ITEM);
                item.setId(id);
                updatedItemList.add(item);
                CartItemResponse itemResponse = new CartItemResponse(id, userId, item.getName(), item.getQuantity(), item.getEntityRefId(), item.getEntityRefType());
                cartItemsResponse.add(itemResponse);
            }
            String cartId = transactionalDao.addCartItems(userId, updatedItemList);
            if (StringUtils.isBlank(cartId)) {
                LOGGER.error(String.format("Error adding items to cart for user: %s", userId));
                throw new StorageException("Error adding items to cart.");
            }

            CartResponse response = new CartResponse(userId, cartItemsResponse);
            return response;

        }  catch (StorageException re) {
            LOGGER.error(String.format("Error adding items to cart for user: %s. Exception %s", session.getUserEmail(), re));
            throw re;
        }
        catch(Exception e) {
            LOGGER.error(String.format("Error adding items to cart for user: %s. Exception %s", session.getUserEmail(), e));
            throw new ResourceCreationException(e.getMessage());
        }
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
