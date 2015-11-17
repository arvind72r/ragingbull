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

    @SqlUpdate("INSERT  INTO user (id, email, name, hash, phone, inlet_type, role, picture_url) " +
            "VALUES(:id, :email, :name, :hash, :phone, :inletType, :role, :pictureUrl)")
    int createUser(@Bind("id") String id,
                   @Bind("name") String name,
                   @Bind("email") String email,
                   @Bind("hash") String hash,
                   @Bind("phone") String phone,
                   @Bind("inletType") String inletType,
                   @Bind("role")Integer role,
                   @Bind("pictureUrl")String pictureUrl);

    @SqlQuery("SELECT * FROM user where email = :email")
    User getByEmail(@Bind("email") String email);

    @SqlUpdate("UPDATE user set verified = true where id = :id")
    int approveUser(@Bind("id") String id);

    @SqlQuery("SELECT * FROM user where name = :name")
    User getByName(@Bind("name") String username);

    @SqlQuery("SELECT hash FROM user where email = :email")
    String getHash(@Bind("email") String email);

    @SqlUpdate("UPDATE USER set hash = :hash where email = :email")
    int updatePassword(@Bind("email") String email,
                       @Bind("hash") String hash);

    @SqlQuery("SELECT * FROM user where id = :id")
    User getById(@Bind("id") String id);

    @SqlUpdate("UPDATE USER set name = :name and email = :email and picture_url = :pictureUrl where id = :id")
    int updateInfo(@Bind("id") String id,
                   @Bind("name") String name,
                   @Bind("email") String email,
                   @Bind("pictureUrl") String pictureUrl);

    @SqlUpdate("UPDATE USER set :field = :data where id = :id")
    int updateInfo(@Bind("id") String id,
                   @Bind("field") String field,
                   @Bind("data") String data);

    @SqlUpdate("UPDATE USER SET picture_url = :pictureUrl where id = :id")
    int pictureUrl(String userId, String absolutePath);
}
