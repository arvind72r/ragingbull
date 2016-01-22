/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Favourite;
import com.medic.ragingbull.api.FavouriteResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.FavouriteService;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class FavouriteAccessService {

    private FavouriteService favouriteService;

    @Inject
    public FavouriteAccessService(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    public Response getFavourites(Session session, String userId) {
        List<FavouriteResponse> response = favouriteService.getFavourites(session, userId);
        return Response.ok().entity(response).build();
    }

    public Response getFavourite(Session session, String userId, String favouriteId) {
        FavouriteResponse response = favouriteService.getFavourite(session, userId, favouriteId);
        return Response.ok().entity(response).build();
    }

    public Response addFavourite(Session session, String userId, Favourite favourite) throws StorageException {
        FavouriteResponse response = favouriteService.addFavourite(session, userId, favourite);
        return Response.ok().entity(response).build();
    }

    public Response deleteFavourite(Session session, String userId, String favouriteId) {
        boolean success = favouriteService.deleteFavourite(session, userId, favouriteId);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}
