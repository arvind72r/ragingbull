/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Mentor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class MentorMapper implements ResultSetMapper<Mentor>{

    @Override
    public Mentor map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Mentor(r.getString("id"), r.getString("user_id"), r.getString("company_id"), r.getInt("upvotes"), r.getString("positive"), r.getString("negative"));
    }
}
