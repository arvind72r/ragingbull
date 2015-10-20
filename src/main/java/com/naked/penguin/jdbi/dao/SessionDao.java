/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.naked.penguin.api.Session;
import com.naked.penguin.jdbi.mapper.BindTimestamp;
import com.naked.penguin.jdbi.mapper.SessionMapper;
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

    @SqlUpdate("INSERT INTO SESSION (token, user_id, data, expiry) VALUES (:token, :user_id, :data, :expiry)")
    public int createSession(@Bind("token") String token, @Bind("user_id")  String userId, @Bind("data") String data, @BindTimestamp("expiry") DateTime expiry);

    @SqlQuery("SELECT * FROM SESSION where token = :token")
    public Session getSession(@Bind("token") String token);

    @SqlQuery("SELECT * FROM SESSION where user_id = :user_id")
    public List<Session> getSessionsPerUser(@Bind("user_id") String user_id);
}
