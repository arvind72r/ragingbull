/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Notes;
import com.medic.ragingbull.jdbi.mapper.NotesMapper;
import org.skife.jdbi.v2.sqlobject.*;
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

    @SqlQuery("SELECT * FROM NOTES where entity_id = :entityId")
    List<Notes> getNotes(@Bind("entityId") String entityId);
}
