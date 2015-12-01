/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.google.common.collect.ImmutableList;
import com.medic.ragingbull.api.EntityAdmin;
import com.medic.ragingbull.jdbi.mapper.EntityUsersMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(EntityUsersMapper.class)
public interface EntityUsersDao {


    @SqlUpdate("INSERT INTO entity_users (id, user_id, created_by, entity_id, entity) VALUES (:id, :userId, :createdBy, :entityId, :entity)")
    int create(@Bind("id") String adminId,
               @Bind("userId") String userId,
               @Bind("createdBy") String createdBy,
               @Bind("entityId") String entityId,
               @Bind("entity") String entity);

    @SqlQuery("SELECT * FROM entity_users where entity_id = :entityId")
    ImmutableList<EntityAdmin> getAll(@Bind("entityId") String entityId);

    @SqlQuery("SELECT * FROM entity_users where entity_id = :entityId and user_id = :userId")
    EntityAdmin getUser(@Bind("entityId") String entityId, @Bind("userId") String userId);
}
