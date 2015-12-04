/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.User;
import com.medic.ragingbull.core.constants.SystemConstants;
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
        return new User(
                r.getString("id"),
                r.getString("name"),
                r.getString("hash"),
                r.getString("email"),
                r.getString("phone"),
                r.getString("inlet_type"),
                r.getString("picture_url"),
                r.getBoolean("verified"),
                r.getBoolean("active"),
                r.getLong("role"),
                SystemConstants.Sex.valueOf(r.getString("sex")),
                new DateTime(r.getTimestamp("dob")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));

    }
}
