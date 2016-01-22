/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Address;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class AddressMapper implements ResultSetMapper<Address>{

    @Override
    public Address map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Address(r.getString("id"),
                r.getString("user_id"),
                r.getString("label"),
                r.getString("address1"),
                r.getString("address2"),
                r.getString("city"),
                r.getString("state"),
                r.getString("landmark"),
                r.getInt("zip")
        );
    }
}
