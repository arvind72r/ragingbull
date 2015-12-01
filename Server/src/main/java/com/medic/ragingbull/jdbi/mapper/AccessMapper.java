/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Access;
import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class AccessMapper implements ResultSetMapper<Access>{
    @Override
    public Access map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Access(r.getString("id"),
                r.getString("user_id"),
                r.getString("code"),
                SystemConstants.AccessEntities.valueOf(r.getString("entity")),
                r.getBoolean("active"),
                new DateTime(r.getTimestamp("expiry")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
