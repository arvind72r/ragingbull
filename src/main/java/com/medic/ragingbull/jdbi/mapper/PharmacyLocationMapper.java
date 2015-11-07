/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.PharmacyLocation;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyLocationMapper implements ResultSetMapper<PharmacyLocation> {
    @Override
    public PharmacyLocation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new PharmacyLocation(
                r.getString("id"),
                r.getString("user_id"),
                r.getString("practitioner_id"),
                r.getString("name"),
                r.getString("description"),
                r.getString("location"),
                r.getString("primary_contact"),
                r.getString("secondary_contact"),
                r.getString("address1"),
                r.getString("address2"),
                r.getString("city"),
                r.getString("state"),
                r.getLong("zip"),
                r.getString("country"),
                r.getString("landmark"),
                r.getFloat("longitude"),
                r.getFloat("latitude"),
                r.getInt("working_hours"),
                r.getInt("working_days"),
                r.getBoolean("verified"),
                r.getBoolean("active"),
                r.getString("license"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
