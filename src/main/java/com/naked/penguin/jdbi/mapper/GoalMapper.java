/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Goal;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class GoalMapper implements ResultSetMapper<Goal> {

    @Override
    public Goal map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return  new Goal(
                r.getString("id"),
                r.getString("user_id"),
                r.getString("name"),
                r.getString("description"),
                new DateTime(r.getTimestamp("expiry")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime((r.getTimestamp("updated_At"))));
    }
}
