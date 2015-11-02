/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.PasswordReset;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PasswordResetMapper.class)
public class PasswordResetMapper implements ResultSetMapper<PasswordReset> {

    @Override
    public PasswordReset map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new PasswordReset(r.getString("id"),
                r.getString("id"),
                r.getBoolean("active"),
                new DateTime(r.getTimestamp("expiry")),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
