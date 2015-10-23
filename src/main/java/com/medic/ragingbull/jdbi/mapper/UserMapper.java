/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.User;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new User(r.getString("id"),
                r.getString("email"),
                r.getString("name"),
                r.getBoolean("verified"),
                r.getBoolean("isNative"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
