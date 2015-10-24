/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import com.medic.ragingbull.jdbi.mapper.SessionMapper;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */

@RegisterMapper(SessionMapper.class)
public interface SessionDao {

    @SqlUpdate("INSERT INTO SESSION (token, user_id, user_email, active, expiry) VALUES (:token, :user_id, :email, true, :expiry)")
    public int createSession(@Bind("token") String token, @Bind("user_id")  String userId, @Bind("email") String email, @BindTimeStamp("expiry") long expiry);

    @SqlQuery("SELECT * FROM SESSION where token = :token")
    public Session getSession(@Bind("token") String token);

    @SqlQuery("SELECT * FROM SESSION where user_id = :user_id")
    public List<Session> getSessionsPerUserId(@Bind("user_id") String user_id);

    @SqlQuery("SELECT * FROM SESSION where active = true and user_email = :user_email")
    public List<Session> getActiveSessionsPerUserEmail(@Bind("user_email") String userEmail);

    @SqlUpdate("UPDATE SESSION set active = false where token = :token")
    public int logoutUser(@Bind("token")  String token);
}
