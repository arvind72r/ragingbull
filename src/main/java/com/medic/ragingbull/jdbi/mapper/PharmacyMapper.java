/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Pharmacy;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyMapper implements ResultSetMapper<Pharmacy> {

    @Override
    public Pharmacy map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Pharmacy(
                r.getString("id"),
                r.getString("name"),
                r.getString("location"),
                Integer.valueOf(r.getString("primary_contact")),
                Integer.valueOf(r.getString("secondary_contact")),
                r.getString("address1"),
                r.getString("address2"),
                r.getString("city"),
                r.getString("state"),
                Long.valueOf(r.getString("zip")),
                r.getString("country"),
                r.getString("landmark"),
                r.getFloat("longitude"),
                r.getFloat("latitude"),
                r.getInt("delivery_radius"),
                r.getInt("working_hours"),
                r.getInt("working_days"),
                r.getBoolean("verified"),
                r.getBoolean("active"),
                r.getString("license"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }

}
