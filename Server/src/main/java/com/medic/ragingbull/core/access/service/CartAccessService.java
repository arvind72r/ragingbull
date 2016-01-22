/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.api.Session;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class CartAccessService {
    public Response getUserCart(Session session, String userId) {
        return null;
    }

    public Response addItemToCart(Session session, String userId, List<CartItem> items) {
        return null;
    }

    public Response removeCartItem(Session session, String userId) {
        return null;
    }

    public Response emptyUserCart(Session session, String userId) {
        return null;
    }

    public Response orderCart(Session session, String userId) {
        return null;
    }
}
