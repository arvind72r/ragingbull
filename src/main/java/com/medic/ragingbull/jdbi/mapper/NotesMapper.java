/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Notes;
import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vamshi Molleti
 */
public class NotesMapper implements ResultSetMapper<Notes> {
    @Override
    public Notes map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Notes(r.getString("id"),
                r.getString("entity_id"),
                SystemConstants.NotesTypes.valueOf(r.getString("entity_type")),
                r.getString("content"),
                r.getBoolean("active"),
                new DateTime(r.getTimestamp("created_at")),
                new DateTime(r.getTimestamp("updated_at")));
    }
}
