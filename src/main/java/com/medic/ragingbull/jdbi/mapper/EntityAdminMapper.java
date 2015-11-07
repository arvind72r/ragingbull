/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.EntityAdmin;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class EntityAdminMapper implements ResultSetMapper<EntityAdmin>{

    @Override
    public EntityAdmin map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new EntityAdmin(
                r.getString("id"),
                r.getString("user_id"),
                r.getString("created_by"),
                r.getString("entity_id"),
                r.getInt("role"),
                r.getString("entity"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at"))
        );
    }
}
