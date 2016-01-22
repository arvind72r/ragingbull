/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Image;
import com.medic.ragingbull.jdbi.mapper.ImagesMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ImagesMapper.class)
public interface ImagesDao {

    @SqlUpdate("INSERT INTO IMAGES (id, user_id, type, path) VALUES (:id, :userId, :type, :path)")
    int createImage(@Bind("id") String id, @Bind("userId") String userId, @Bind("type") String type, @Bind("path") String path);

    @SqlQuery("SELECT * FROM IMAGES where id = :id AND user_id = :userId")
    Image getById(@Bind("id") String id, @Bind("userId") String userId);

    @SqlQuery("SELECT * FROM IMAGES where user_id = :userId")
    List<Image> getByUserId(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM IMAGES where user_id = :userId AND type = 'prescription'")
    List<Image> getPrescriptionByUserId(@Bind("userId") String userId);
}
