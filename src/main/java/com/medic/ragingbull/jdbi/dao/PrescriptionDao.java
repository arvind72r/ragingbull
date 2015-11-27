/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.api.Prescription;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public interface PrescriptionDao {


    @SqlQuery("SELECT * FROM prescription where id = :id")
    Prescription getPrescription(String prescriptionId);

    @SqlUpdate("INSERT INTO prescription (id, consultation_id, practitionerId, user_id ) " +
            "VALUES(:id, :consultationId, :practitionerId, :userId)")
    int createPrescription(@Bind("id") String id,
                           @Bind("consultationId") String consultationId,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("userId") String userId);

}
