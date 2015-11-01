/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Consultation;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class ConsultationMapper implements ResultSetMapper<Consultation> {
    @Override
    public Consultation map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new Consultation(
                r.getString("id"),
                r.getString("practitioner_id"),
                r.getString("location_id"),
                r.getString("user_id"),
                r.getString("name"),
                r.getInt("slot"),
                r.getString("notes"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
