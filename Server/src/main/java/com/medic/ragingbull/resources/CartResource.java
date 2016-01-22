/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.CartAccessService;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@Path("/user/{id}/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    private CartAccessService cartAccessService;
    @GET
    @Path("/{id}/cart")
    public Response getUserCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return cartAccessService.getUserCart(session, userId);
    }

    @PUT
    @Path("/{id}/cart")
    public Response addItemToCart(@Auth Session session, @PathParam("id") String userId, @Valid List<CartItem> items) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return cartAccessService.addItemToCart(session, userId, items);
    }

    @DELETE
    @Path("/{id}/cart/{itemId}")
    public Response removeCartItem(@Auth Session session, @PathParam("id") String userId){
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return cartAccessService.removeCartItem(session, userId);
    }

    @DELETE
    @Path("/{id}/cart")
    public Response emptyCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return cartAccessService.emptyUserCart(session, userId);
    }

    @POST
    @Path("/{id}/cart/order")
    public Response orderCart(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return cartAccessService.orderCart(session, userId);
    }
}
