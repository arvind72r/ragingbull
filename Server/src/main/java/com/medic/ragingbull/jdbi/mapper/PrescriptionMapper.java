/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Prescription;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class PrescriptionMapper implements ResultSetMapper<Prescription> {

    @Override
    public Prescription map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Prescription(
                r.getString("id"),
                r.getString("consultation_id"),
                r.getString("practitioner_id"),
                r.getString("user_id"),
                r.getBoolean("active"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
