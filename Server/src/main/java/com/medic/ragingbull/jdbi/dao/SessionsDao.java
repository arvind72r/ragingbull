/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import com.medic.ragingbull.jdbi.mapper.SessionMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */

@RegisterMapper(SessionMapper.class)
public interface SessionsDao {

    @SqlUpdate("INSERT INTO SESSIONS (id, user_id, user_email, role, expiry) VALUES (:token, :user_id, :email, :role, :expiry)")
    public int createSession(@Bind("token") String token,
                             @Bind("user_id") String userId,
                             @Bind("email") String email,
                             @Bind("role") Long role,
                             @BindTimeStamp("expiry") long expiry);

    @SqlQuery("SELECT * FROM SESSIONS where id = :token")
    public Session getSession(@Bind("token") String token);

    @SqlQuery("SELECT * FROM SESSIONS where user_id = :user_id")
    public List<Session> getSessionsPerUserId(@Bind("user_id") String user_id);

    @SqlQuery("SELECT * FROM SESSIONS where user_email = :user_email")
    public List<Session> getActiveSessionsPerUserEmail(@Bind("user_email") String userEmail);

    @SqlUpdate("DELETE FROM SESSIONS where id = :token")
    public int logoutUser(@Bind("token") String token);

    @SqlUpdate("DELETE FROM SESSIONS")
    int cleanseAll();
}
