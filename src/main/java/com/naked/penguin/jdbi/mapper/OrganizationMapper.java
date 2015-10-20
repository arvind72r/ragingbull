/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Organization;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class OrganizationMapper implements ResultSetMapper<Organization> {

    @Override
    public Organization map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Organization(r.getString("id"), r.getString("name"), r.getString("type"), new DateTime(r.getTimestamp("validity")));
    }
}
