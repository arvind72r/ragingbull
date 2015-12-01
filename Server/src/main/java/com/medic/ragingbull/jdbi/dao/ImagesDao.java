/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.jdbi.mapper.ImagesMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ImagesMapper.class)
public interface ImagesDao {

    @SqlUpdate("INSERT INTO IMAGES (id, entity_id, path) VALUES (:id, :entityId, :path)")
    int createImage(@Bind("id") String id, @Bind("entityId") String entityId, @Bind("path") String path);

    @SqlQuery("SELECT * FROM IMAGES where id = :id")
    int getById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM IMAGES where entity_id = :entityId")
    int getByEntityId(@Bind("entityId") String entityId);
}
