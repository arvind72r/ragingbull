/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Practitioner;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerMapper implements ResultSetMapper<Practitioner> {
    @Override
    public Practitioner map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new Practitioner(
                r.getString("id"),
                r.getString("user_id"),
                r.getString("description"),
                r.getString("primary_contact"),
                r.getString("secondary_contact"),
                r.getString("primary_id"),
                r.getString("secondary_id"),
                r.getString("registration_id"),
                r.getString("registration_authority"),
                r.getString("license"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
