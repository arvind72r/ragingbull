/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Access;
import com.medic.ragingbull.jdbi.mapper.AccessMapper;
import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(AccessMapper.class)
public interface AccessDao {

    @SqlUpdate("INSERT INTO access (id, user_id, code, entity, expiry) " +
            "VALUES(:id, :user_id, :code, :entity, :expiry)")
    int create(@Bind("id") String id,
               @Bind("user_id") String user_id,
               @Bind("code") String code,
               @Bind("entity") String entity,
               @BindTimeStamp("expiry") long expiry);

    @SqlQuery("SELECT * FROM access where id = :id")
    Access getById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM access where user_id = :userId")
    List<Access> getByUserId(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM access where user_id = :userId and active = true")
    Access getActiveByUserId(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM access where code = :code")
    Access getByCode(@Bind("code") String code);

    @SqlUpdate("DELETE FROM access")
    int cleanseAll();
}
