/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class Mapper implements ResultSetMapper<Map<String,Object>>{

    @Override
    public Map<String, Object> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Map<String, Object> mapper = new HashMap<>();
        ResultSetMetaData metaData = r.getMetaData();
        for (int columnNumber = 1; columnNumber <=  metaData.getColumnCount(); columnNumber++) {
            mapper.put(metaData.getColumnLabel(columnNumber), r.getObject(columnNumber));
        }
        return mapper;
    }
}
