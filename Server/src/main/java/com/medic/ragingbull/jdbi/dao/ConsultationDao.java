/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.jdbi.mapper.ConsultationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ConsultationMapper.class)
public interface ConsultationDao  {

    @SqlQuery("SELECT * FROM consultation where id = :id")
    Consultation getConsultation(@Bind("id") String id);

    @SqlUpdate("INSERT INTO consultation (id, location_id, practitioner_id, user_id, creator_id) " +
            "VALUES(:id, :locationId, :practitionerId, :userId, :creatorId)")
    int createConsultation(@Bind("id") String consultationId,
                           @Bind("locationId") String locationId,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("userId") String userId,
                           @Bind("creatorId") String creatorId);

    @SqlUpdate("UPDATE CONSULTATION set active = false where id = :id and location_id = :locationId")
    int deleteConsultation(@Bind("id") String id, @Bind("locationId") String locationId);


//    @SqlUpdate("INSERT INTO consultation (id, practitioner_id, location_id, user_id, name, slot, notes) " +
//            "VALUES(:id, :practitionerId, :locationId, :userId, :name, :slot, :notes)")
//    int createConsultation(@Bind("id") String consultationId,
//                           @Bind("practitionerId") String practitionerId,
//                           @Bind("locationId") String locationId,
//                           @Bind("userId") String userId,
//                           @Bind("name") String name,
//                           @Bind("slot") Integer slot,
//                           @Bind("notes") String notes);
}
