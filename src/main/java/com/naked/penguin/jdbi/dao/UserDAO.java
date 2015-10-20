/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.naked.penguin.api.User;
import com.naked.penguin.jdbi.mapper.BindTimestamp;
import com.naked.penguin.jdbi.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

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

    @SqlUpdate("UPDATE user set verified = 0 where id = :id")
    int approveUser(@Bind("id") String id);

    @SqlQuery("SELECT * FROM user where name = :name")
    User getByName(@Bind("name") String username);

    @SqlQuery("SELECT hash FROM user where email = :email")
    String getHash(@Bind("email") String email);
}
