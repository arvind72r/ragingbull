/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Image;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class ImagesMapper implements ResultSetMapper<Image>{
    @Override
    public Image map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Image(r.getString("id"),
                r.getString("entity_id"),
                r.getString("path"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at"))
                );
    }
}
