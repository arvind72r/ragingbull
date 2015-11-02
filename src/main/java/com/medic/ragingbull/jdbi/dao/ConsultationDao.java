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
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ConsultationMapper.class)
public interface ConsultationDao  {

    @SqlQuery("SELECT * FROM consultation where id = :id")
    Consultation getConsultation(String practitionerId, String locationId, String consultId);

    @SqlUpdate("INSERT INTO consultation (id, practitioner_id, location_id, user_id, name, slot, notes) " +
            "VALUES(:id, :practitionerId, :locationId, :userId, :name, :slot, :notes)")
    int createConsultation(@Bind("id") String consultationId,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("locationId") String locationId,
                           @Bind("userId") String userId,
                           @Bind("name") String name,
                           @Bind("slot") Integer slot,
                           @Bind("notes") String notes);
}
