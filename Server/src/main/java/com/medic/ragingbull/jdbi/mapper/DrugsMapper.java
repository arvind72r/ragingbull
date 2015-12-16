/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Dosage;
import com.medic.ragingbull.api.Drug;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class DrugsMapper implements ResultSetMapper<Drug> {

    @Override
    public Drug map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Drug(
                r.getString("id"),
                r.getString("consultation_id"),
                r.getString("practitioner_id"),
                r.getString("prescriptionId"),
                r.getString("user_id"),
                r.getString("name"),
                r.getInt("frequency"),
                Dosage.Schedule.valueOf(r.getString("schedule")),
                r.getInt("dose"),
                r.getString("unit"),
                r.getInt("days"),
                r.getBoolean("active"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
