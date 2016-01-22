/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Favourite;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class FavouriteMapper implements ResultSetMapper<Favourite>{

    @Override
    public Favourite map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Favourite(r.getString("id"),
                r.getString("user_id"),
                r.getString("label"),
                r.getString("entity_type"),
                r.getString("entity_id"));
    }
}
