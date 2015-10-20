/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.naked.penguin.api.Organization;
import com.naked.penguin.jdbi.mapper.BindTimestamp;
import com.naked.penguin.jdbi.mapper.OrganizationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(OrganizationMapper.class)
public interface OrganizationDao {

    @SqlUpdate("INSERT INTO ORG (id, name, type, validity) values(:id, :name, :type, :validity) ")
    int create(@Bind("id") String id, @Bind("name") String name, @Bind("type") String type, @BindTimestamp("validity")long validity);

    @SqlQuery("SELECT * FROM ORG where name = :name")
    Organization get(@Bind("name") String name);
}
