/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.medic.ragingbull.core.services.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloRagingBull {

    private UserService userService;

    @Inject
    public HelloRagingBull(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Timed
    public Response sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format("RagingBull Says Hi Mr. %s", name.or("X"));
        return Response.ok().entity(value).build();
    }
}
