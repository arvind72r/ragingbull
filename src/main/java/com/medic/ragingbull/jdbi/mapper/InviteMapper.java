/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Invite;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class InviteMapper implements ResultSetMapper<Invite> {
    @Override
    public Invite map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Invite(r.getString("id"),
                r.getString("user_id"),
                r.getInt("code"),
                new DateTime(r.getTimestamp("expiry")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}