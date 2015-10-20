/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Session;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class SessionMapper implements ResultSetMapper<Session> {
    @Override
    public Session map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Session(r.getString("token"), r.getString("user_id"), r.getString("data"), new DateTime(r.getDate("expiry")), new DateTime(r.getDate("created_at")));
    }
}
