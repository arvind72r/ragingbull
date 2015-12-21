/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.jdbi.mapper.ConsultationDetailsMapper;
import com.medic.ragingbull.jdbi.mapper.ConsultationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ConsultationMapper.class)
public interface ConsultationDao  {

    @SqlQuery("SELECT * FROM consultation where id = :id")
    Consultation getConsultation(@Bind("id") String id);

    @SqlQuery("SELECT cn.*, us.name as userName, us.phone as userPhone, us.dob as userDob, pr.name as practitionerName FROM CONSULTATION cn, USERS us, USERS pr , PRACTITIONER practitioner where cn.id = :id " +
            "and us.id = cn.user_id " +
            "and practitioner.id = cn.practitioner_id " +
            "and pr.id = practitioner.user_id")
    @Mapper(ConsultationDetailsMapper.class)
    Consultation getConsultationDetails(@Bind("id") String id);

    @SqlUpdate("INSERT INTO consultation (id, location_id, practitioner_id, user_id, creator_id, symptoms, diagnosis, user_notes) " +
            "VALUES(:id, :locationId, :practitionerId, :userId, :creatorId, :symptoms, :diagnosis, :userNotes)")
    int createConsultation(@Bind("id") String consultationId,
                           @Bind("locationId") String locationId,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("userId") String creatorId,
                           @Bind("creatorId") String userId,
                           @Bind("symptoms") String symptoms,
                           @Bind("diagnosis") String diagnosis,
                           @Bind("userNotes") String userNotes);

    @SqlUpdate("UPDATE CONSULTATION set active = false where id = :id")
    int deleteConsultation(@Bind("id") String id);

    @SqlQuery("SELECT * FROM consultation where user_id = :userId and active = true")
    List<Consultation> getActiveConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM consultation where user_id = :userId and active = false")
    List<Consultation> getPastConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM consultation cs, practitioner pr where cs.practitioner_id = pr.id AND pr.user_id = :userId AND cs.active = true")
    List<Consultation> getActivePractitionerConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM consultation where practitioner_id = :practitionerId and active = false")
    List<Consultation> getPastPractitionerConsultations(@Bind("practitionerId") String practitionerId);

    @SqlUpdate("DELETE FROM consultation")
    int cleanAll();

    @SqlQuery("SELECT * FROM consultation cn, users us where cn.active = true and cn.user_id  = us.id and us.parent_id = :userId")
    List<Consultation> getActiveMemberConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM consultation cn, users us where cn.active = false and cn.user_id  = us.id and us.parent_id = :userId")
    List<Consultation> getPastMemberConsultations(@Bind("userId") String userId);
}
