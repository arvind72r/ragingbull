/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import com.naked.penguin.api.Athlete;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class AthleteMapper implements ResultSetMapper<Athlete>{

    @Override
    public Athlete map(int i, ResultSet r, StatementContext cntx) throws SQLException {
        return new Athlete(r.getString("id"), r.getString("user_id"), r.getString("training_user_id"), r.getString("level"));
    }
}
