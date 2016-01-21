/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Prescription;
import com.medic.ragingbull.jdbi.mapper.PrescriptionMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PrescriptionMapper.class)
public interface PrescriptionDao {


    @SqlQuery("SELECT * FROM prescription where id = :id AND consultation_id = :consultationId")
    Prescription getPrescription(@Bind("id") String id, @Bind("consultationId") String consultationId);

    @SqlUpdate("INSERT INTO prescription (id, consultation_id, practitioner_id, user_id ) " +
            "VALUES(:id, :consultationId, :practitionerId, :userId)")
    int createPrescription(@Bind("id") String id,
                           @Bind("consultationId") String consultationId,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("userId") String userId);

    @SqlUpdate("UPDATE PRESCRIPTION set active = false where id = :id")
    int deletePrescription(@Bind("id") String id);

    @SqlQuery("SELECT * FROM prescription where id = :id")
    Prescription getPrescription(@Bind("id") String id);

    @SqlQuery("SELECT * FROM prescription where consultation_id = :consultationId")
    Prescription getCurrentPrescription(@Bind("consultationId") String consultationId);

    @SqlQuery("SELECT * FROM prescription where user_id = :userId")
    List<Prescription> getPrescriptions(@Bind("userId") String userId);
}
