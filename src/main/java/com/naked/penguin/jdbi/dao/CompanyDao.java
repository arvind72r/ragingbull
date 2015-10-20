/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.naked.penguin.api.Company;
import com.naked.penguin.jdbi.mapper.CompanyMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(CompanyMapper.class)
public interface CompanyDao {
    @SqlUpdate("INSERT INTO COMPANY (id, name, display_name, intro) values (:id, :name, :displayName, :intro)")
    int create(@Bind("id") String id, @Bind("name") String name, @Bind("displayName") String displayName, @Bind("intro") String intro);

    @SqlQuery("SELECT * FROM COMPANY where name = :companyName")
    Company get(@Bind("companyName") String companyName);

    @SqlUpdate("UPDATE COMPANY SET display_name = :displayName, intro = :intro where name = :companyName")
    int update(@Bind("companyName") String companyName, @Bind("displayName") String displayName, @Bind("intro") String intro);
}
