/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.core.constants.LocationSpeciality;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerLocationMapper implements ResultSetMapper<PractitionerLocation> {

    @Override
    public PractitionerLocation map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new PractitionerLocation(
                r.getString("id"),
                r.getString("practitioner_id"),
                r.getString("user_id"),
                r.getString("name"),
                r.getString("description"),
                LocationSpeciality.valueOf(r.getString("speciality")),
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
