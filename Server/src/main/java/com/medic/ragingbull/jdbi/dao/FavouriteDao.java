/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Favourite;
import com.medic.ragingbull.jdbi.mapper.FavouriteMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(FavouriteMapper.class)
public interface FavouriteDao {

    @SqlQuery("SELECT * FROM user_favourites where user_id : userId")
    List<Favourite> getFavourites(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM user_favourites where id = :id AND user_id = :userId")
    Favourite getFavourite(@Bind("id") String id, @Bind("userId") String userId);

    @SqlUpdate("INSERT INTO user_favourites (id, user_id, label, entity_type, entity_id) VALUES (:id, :label, :entityType, :entityId)")
    int insert(@Bind("id") String id,
            @Bind("userId") String userId,
            @Bind("label") String label,
            @Bind("entityType") String entityType,
            @Bind("entityId") String entityId);

    @SqlUpdate("DELETE FROM user_favourites where id = :id AND user_id = :userId")
    int delete(@Bind("id") String id, @Bind("userId") String userId);
}
