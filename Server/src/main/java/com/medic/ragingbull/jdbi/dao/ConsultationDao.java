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

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ConsultationMapper.class)
public interface ConsultationDao  {

    @SqlQuery("SELECT * FROM consultation where id = :id")
    Consultation getConsultation(@Bind("id") String id);

    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cn.* FROM consultation cn, practitioner_location location, practitioner pr, users consultee, users doctor where consultee.id = cn.user_id AND doctor.id = pr.user_id AND location.id = cn.location_id AND pr.id = cn.practitioner_id AND cn.id = :id and cn.active = true")
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

    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cn.* FROM consultation cn, practitioner_location location, practitioner pr, users consultee, users doctor where consultee.id = cn.user_id AND doctor.id = pr.user_id AND location.id = cn.location_id AND pr.id = cn.practitioner_id AND cn.user_id = :userId and cn.active = true")
    List<Consultation> getActiveConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cn.* FROM consultation cn, practitioner_location location, practitioner pr, users consultee, users doctor where consultee.id = cn.user_id AND doctor.id = pr.user_id AND location.id = cn.location_id AND pr.id = cn.practitioner_id AND cn.user_id = :userId and cn.active = false")
    List<Consultation> getPastConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cs.* FROM consultation cs, practitioner pr, practitioner_location location, users consultee, users doctor where consultee.id = cs.user_id AND doctor.id = pr.user_id AND location.id = cs.location_id AND cs.practitioner_id = pr.id AND pr.user_id = :userId AND cs.active = true ;")
    List<Consultation> getActivePractitionerConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM consultation where practitioner_id = :practitionerId and active = false")
    List<Consultation> getPastPractitionerConsultations(@Bind("practitionerId") String practitionerId);

    @SqlUpdate("DELETE FROM consultation")
    int cleanAll();


    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cn.* FROM consultation cn, practitioner_location location, practitioner pr, users consultee, users doctor where consultee.id = cn.user_id AND doctor.id = pr.user_id AND location.id = cn.location_id AND pr.id = cn.practitioner_id AND cn.user_id = consultee.id and consultee.parent_id = :userId and cn.active = true")
    List<Consultation> getActiveMemberConsultations(@Bind("userId") String userId);

    @SqlQuery("SELECT consultee.name, consultee.dob, consultee.phone, doctor.name as doctorName, location.location, cn.* FROM consultation cn, practitioner_location location, practitioner pr, users consultee, users doctor where consultee.id = cn.user_id AND doctor.id = pr.user_id AND location.id = cn.location_id AND pr.id = cn.practitioner_id AND cn.user_id = consultee.id and consultee.parent_id = :userId and cn.active = false")
    List<Consultation> getPastMemberConsultations(@Bind("userId") String userId);

}
