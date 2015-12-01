/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Notes;
import com.medic.ragingbull.jdbi.mapper.NotesMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(NotesMapper.class)
public interface NotesDao {


    @SqlBatch("INSERT INTO NOTES (id, entity_id, entity_type, content) VALUES (:id, :entityId, :entityType, :content)")
    int createAll(@BindBean List<Notes> consultationNotes);

    @SqlUpdate("INSERT INTO NOTES (id, entity_id, entity_type, content) VALUES (:id, :entityId, :entityType, :content)")
    int create(@Bind("id") String id,
               @Bind("entityId") String entityId,
               @Bind("entityType") String entityType,
               @Bind("content") String content);

    @SqlUpdate("UPDATE NOTES SET active = false where id = :id and entityId = :entityId")
    int deleteNotes(@Bind("id") String id, @Bind("entityId") String entityId);
}
