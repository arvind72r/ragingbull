/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.google.common.collect.ImmutableList;
import com.medic.ragingbull.api.EntityAdmin;
import com.medic.ragingbull.jdbi.mapper.EntityAdminMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(EntityAdminMapper.class)
public interface EntityAdminDao {


    @SqlUpdate("INSERT INTO entity_admin (id, user_id, created_by, entity_id, role, entity) VALUES (:id, :userId, :createdBy, :entityId, :role, :entity)")
    int create(@Bind("id") String adminId,
               @Bind("userId") String userId,
               @Bind("createdBy") String createdBy,
               @Bind("entityId") String entityId,
               @Bind("role") Integer role,
               @Bind("entity") String entity);

    @SqlQuery("SELECT * FROM entity_admin where entity_id = :entityId")
    ImmutableList<EntityAdmin> getAll(@Bind("entityId") String entityId);

    @SqlQuery("SELECT * FROM entity_admin where entity_id = :entityId and user_id = :userId")
    EntityAdmin getUser(@Bind("entityId") String entityId, @Bind("userId") String userId);
}
