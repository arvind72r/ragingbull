/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Company;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class CompanyMapper implements ResultSetMapper<Company> {

    @Override
    public Company map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return  new Company(r.getString("id"), r.getString("name"), r.getString("display_name"), r.getString("intro"), new DateTime(r.getTimestamp("created_at")), new DateTime(r.getTimestamp("updated_at")));
    }
}
