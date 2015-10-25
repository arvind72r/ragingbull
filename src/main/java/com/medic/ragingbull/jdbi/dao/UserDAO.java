/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.User;
import com.medic.ragingbull.jdbi.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlUpdate("INSERT INTO user (id, email, name, hash, verified, isNative) " +
            "VALUES(:id, :email, :name, :hash, :verified, :isNative)")
    int createUser(@Bind("id") String id,
                   @Bind("email") String email,
                   @Bind("name") String name,
                   @Bind("hash") String hash,
                   @Bind("verified") Boolean verified,
                   @Bind("isNative") Boolean isNative);

    @SqlQuery("SELECT * FROM user where email = :email")
    User getByEmail(@Bind("email") String email);

    @SqlUpdate("UPDATE user set verified = true where id = :id")
    int approveUser(@Bind("id") String id);

    @SqlQuery("SELECT * FROM user where name = :name")
    User getByName(@Bind("name") String username);

    @SqlQuery("SELECT hash FROM user where email = :email")
    String getHash(@Bind("email") String email);
}
