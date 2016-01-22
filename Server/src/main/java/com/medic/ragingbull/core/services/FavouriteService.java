/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Favourite;
import com.medic.ragingbull.api.FavouriteResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.FavouriteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class FavouriteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavouriteService.class);
    private FavouriteDao favouriteDao;

    @Inject
    public FavouriteService(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    public List<FavouriteResponse> getFavourites(Session session, String userId) {
        List<FavouriteResponse> responseList = new ArrayList<>();
        List<Favourite> favourites = favouriteDao.getFavourites(userId);
        for (Favourite favourite : favourites) {
            responseList.add(new FavouriteResponse(favourite.getId(), favourite.getLabel(), favourite.getEntityType(), favourite.getEntityId()));
        }
        return responseList;
    }

    public FavouriteResponse getFavourite(Session session, String userId, String favouriteId) {
        Favourite favourite = favouriteDao.getFavourite(favouriteId, userId);
        if (favourite == null) {
            return null;
        }
        return new FavouriteResponse(favourite.getId(), favourite.getLabel(), favourite.getEntityType(), favourite.getEntityId());
    }

    public FavouriteResponse addFavourite(Session session, String userId, Favourite favourite) throws StorageException {
        favourite.setId(com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER_FAVOURITES));
        int favouriteCreated = favouriteDao.insert(favourite.getId(), userId, favourite.getLabel(), favourite.getEntityType(), favourite.getEntityId());

        if (favouriteCreated == 0) {
            LOGGER.error(String.format("Error adding favourite for user %s. DB failed to save the record", session.getUserEmail()));
            throw new StorageException("Error adding user favourite. Please try again");
        }
        return new FavouriteResponse(favourite.getId(), favourite.getLabel(), favourite.getEntityType(), favourite.getEntityId());
    }

    public boolean deleteFavourite(Session session, String userId, String favouriteId) {
        int favouriteDeleted = favouriteDao.delete(favouriteId, userId);

        if (favouriteDeleted == 0) {
            return false;
        }
        return true;
    }
}
