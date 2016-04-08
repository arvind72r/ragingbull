/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.api.CartResponse;
import com.medic.ragingbull.api.OrderResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.CartService;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.exception.ResourceCreationException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class CartAccessService {

    private CartService cartService;

    @Inject
    public CartAccessService(CartService cartService) {
        this.cartService = cartService;
    }

    public Response getUserCart(Session session, String userId) {

        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            List<CartResponse> responses = cartService.getUserCart(session, userId);
            if (responses != null) {
                return Response.ok().entity(responses).build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public Response addItemToCart(Session session, String userId, List<CartItem> items) throws ResourceCreationException,StorageException{
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            CartResponse response = cartService.addItemToCart(session, userId, items);
            if (response != null) {
                return Response.ok().entity(response).build();
            } else {
                return Response.serverError().build();
            }

        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public Response removeCartItem(Session session, String userId) {

        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            boolean success = cartService.removeCartItem(session, userId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public Response emptyUserCart(Session session, String userId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            boolean success = cartService.emptyUserCart(session, userId);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public Response orderCart(Session session, String userId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            OrderResponse response = cartService.orderCart(session, userId);
            if (response != null) {
                return Response.ok().entity(response).build();
            } else {
                return Response.serverError().build();
            }

        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
