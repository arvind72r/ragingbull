/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Address;
import com.medic.ragingbull.api.Favourite;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.FavouriteAccessService;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/user/{id}/favourite")
@Produces(MediaType.APPLICATION_JSON)
public class FavouriteResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResource.class);
    private FavouriteAccessService favouriteAccessService;

    @Inject
    public FavouriteResource(FavouriteAccessService favouriteAccessService) {
        this.favouriteAccessService = favouriteAccessService;
    }

    @GET
    public Response getFavourites(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = favouriteAccessService.getFavourites(session, userId);
        return response;
    }

    @GET
    @Path("/{favouriteId}")
    public Response getFavourite(@Auth Session session, @PathParam("id") String userId, @PathParam("favouriteId") String favouriteId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = favouriteAccessService.getFavourite(session, userId, favouriteId);
        return response;
    }

    @POST
    public Response addFavourite(@Auth Session session, @PathParam("id") String userId, @Valid Favourite favourite) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = favouriteAccessService.addFavourite(session, userId, favourite);
        return response;
    }

    @DELETE
    @Path("/{favouriteId}")
    public Response deleteFavourite(@Auth Session session, @PathParam("id") String userId, @PathParam("favouriteId") String favouriteId) throws StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        Response response = favouriteAccessService.deleteFavourite(session, userId, favouriteId);
        return response;
    }
}
