/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.User;
import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import com.medic.ragingbull.jdbi.mapper.UserMapper;
import org.h2.jdbc.JdbcSQLException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(UserMapper.class)
public interface UsersDao {

    @SqlUpdate("INSERT  INTO users (id, email, name, hash, phone, inlet_type, role, picture_url, sex, dob) " +
            "VALUES(:id, :email, :name, :hash, :phone, :inletType, :role, :pictureUrl,:sex, :dob)")
    int createUser(@Bind("id") String id,
                   @Bind("name") String name,
                   @Bind("email") String email,
                   @Bind("hash") String hash,
                   @Bind("phone") String phone,
                   @Bind("inletType") String inletType,
                   @Bind("role") Long role,
                   @Bind("pictureUrl") String pictureUrl,
                   @Bind("sex") String sex,
                   @BindTimeStamp("dob") Long dob) throws JdbcSQLException;

    @SqlUpdate("INSERT  INTO users (id, parent_id, email, name, hash, phone, inlet_type, role, sex, dob) " +
            "VALUES(:id, :parentId, :email, :name, :hash, :phone, :inletType, :role, :sex, :dob)")
    int createMember(@Bind("id") String id,
                     @Bind("parentId")String userId,
                     @Bind("name")String name,
                     @Bind("email")String email,
                     @Bind("hash")String hashPass,
                     @Bind("phone")String phone,
                     @Bind("inletType")String inletType,
                     @Bind("role")Long role,
                     @Bind("sex") String sex,
                     @BindTimeStamp("dob") long dob);

    @SqlQuery("SELECT * FROM users where email = :email")
    User getByEmail(@Bind("email") String email);

    @SqlUpdate("UPDATE users set verified = true where id = :id")
    int approveUser(@Bind("id") String id);

    @SqlQuery("SELECT * FROM users where name = :name")
    User getByName(@Bind("name") String username);

    @SqlQuery("SELECT hash FROM users where id = :id")
    String getHashById(@Bind("id") String id);

    @SqlQuery("SELECT hash FROM users where email = :email")
    String getHashByEmail(@Bind("email") String email);

    @SqlUpdate("UPDATE users set hash = :hash where email = :email")
    int updatePasswordByEmail(@Bind("email") String email,
                              @Bind("hash") String hash);

    @SqlUpdate("UPDATE users set hash = :hash where id = :id")
    int updatePasswordById(@Bind("id") String id,
                           @Bind("hash") String hash);

    @SqlQuery("SELECT * FROM users where id = :id")
    User getById(@Bind("id") String id);

    @SqlUpdate("UPDATE users set name = :name and email = :email and picture_url = :pictureUrl where id = :id")
    int updateInfo(@Bind("id") String id,
                   @Bind("name") String name,
                   @Bind("email") String email,
                   @Bind("pictureUrl") String pictureUrl);

    @SqlUpdate("UPDATE users set :field = :data where id = :id")
    int updateInfo(@Bind("id") String id,
                   @Bind("field") String field,
                   @Bind("data") String data);

    @SqlUpdate("UPDATE users SET picture_url = :pictureUrl where id = :id")
    int pictureUrl(String userId, String absolutePath);

    @SqlQuery("SELECT id FROM users where id = :id")
    String getId(@Bind("id") String id);

    @SqlQuery("SELECT * FROM users where id = :id")
    User getRoleById(@Bind("id") String id);

    @SqlUpdate("UPDATE users SET role = :role where id = :id")
    int updateRoleById(@Bind("id") String id, @Bind("role") Long role);

    @SqlQuery("SELECT * FROM users where parent_id = :id")
    List<User> getUsersByParent(@Bind("id") String id);

    @SqlUpdate("DELETE FROM USERS")
    int cleanseAll();

    @SqlUpdate("UPDATE USERS SET phone = :phone where id = :id AND phone IS NULL")
    int updatePhone(@Bind("id") String id, @Bind("phone") String phone);
}
