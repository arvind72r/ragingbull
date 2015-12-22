/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.google.common.collect.ImmutableList;
import com.medic.ragingbull.api.EntityUser;
import com.medic.ragingbull.jdbi.mapper.EntityUsersMapper;
import com.medic.ragingbull.jdbi.mapper.Mapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
import java.util.Map;

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
    ImmutableList<EntityUser> getAll(@Bind("entityId") String entityId);

    @SqlQuery("SELECT * FROM entity_users where entity_id = :entityId and user_id = :userId")
    EntityUser getUser(@Bind("entityId") String entityId, @Bind("userId") String userId);

    @RegisterMapper(Mapper.class)
    @SqlQuery("SELECT usr.name, pr.id as practitionerId, eu.user_id, pl.location, pl.id as locationId FROM USERS usr, PRACTITIONER pr, " +
            "PRACTITIONER_LOCATION pl, ENTITY_USERS eu " +
            "WHERE usr.id = pr.user_id AND pr.user_id = eu.user_id AND pl.id = eu.entity_id AND eu.entity = :entity")
    List<Map<String, Object>> getAllByType(@Bind("entity") String entity);

    @SqlUpdate("DELETE FROM entity_users")
    int cleanAll();
}
