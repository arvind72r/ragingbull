/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.EntityUser;
import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class EntityUsersMapper implements ResultSetMapper<EntityUser>{

    @Override
    public EntityUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new EntityUser(
                r.getString("id"),
                r.getString("user_id"),
                r.getString("created_by"),
                r.getString("entity_id"),
                SystemConstants.Entities.valueOf(r.getString("entity")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at"))
        );
    }
}
