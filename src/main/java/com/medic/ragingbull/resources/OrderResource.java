/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.Order;
import com.medic.ragingbull.api.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/order/")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationResource.class);

    @GET
    public OrderResponse searchOrders(@QueryParam("area") Optional<String> area, @QueryParam("type") Optional<String> type) {
        return null;
    }

    @GET
    @Path("/{id}")
    public OrderResponse getOrders(@PathParam("practId") Optional<String> practId, @PathParam("id") Optional<String> id) {
        return null;
    }

    @POST
    public OrderResponse createOrder(Order order) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public OrderResponse modifyOrder(Order order) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public OrderResponse deleteOrder(Order order) {
        return null;
    }
}
