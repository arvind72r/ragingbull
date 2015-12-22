/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.jdbi.mapper.BindDrug;
import com.medic.ragingbull.jdbi.mapper.DrugsMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(DrugsMapper.class)
public interface DrugsDao {

    @SqlBatch("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, prescription_id, name, frequency, schedule, dose, unit, days) " +
            "VALUES (:id, :consultationId, :userId, :practitionerId, :prescriptionId, :name, :frequency, :schedule, :dose, :unit, :days)")
    int[] createAll(@BindDrug List<Drug> drugs);

    @SqlQuery("SELECT * FROM PRESCRIPTION_DRUG where prescription_id = :prescriptionId")
    List<Drug> getByPrescriptionId(@Bind("prescriptionId") String prescriptionId);

    @SqlUpdate("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, prescription_id, name, manufacturer, quantity, frequency, allergies) " +
            "VALUES (:id, :consultationId, :userId, :practitionerId, :prescriptionId, :name, :manufacturer, :quantity, :frequency, :allergies)")
    int add(@BindDrug Drug drug);

    @SqlUpdate("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, prescription_id, name, manufacturer, quantity, frequency, allergies) " +
            "VALUES (:id, :consultationId, :userId, :practitionerId, :prescriptionId, :name, :manufacturer, :quantity, :frequency, :allergies)")
    int add(@Bind("id") String drugId,
            @Bind("consultationId") String consultationId,
            @Bind("userId") String userId,
            @Bind("practitionerId") String practitionerId,
            @Bind("prescriptionId") String prescriptionId,
            @Bind("name") String name,
            @Bind("manufacturer") String manufacturer,
            @Bind("quantity") Integer quantity,
            @Bind("frequency") Long frequency,
            @Bind("allergies") String allergies);

    @SqlUpdate("UPDATE PRESCRIPTION_DRUG SET active = false WHERE id = :id AND prescription_id = :prescriptionId")
    int removeDrug(@Bind("id") String id, @Bind("prescriptionId") String prescriptionId);

    @SqlUpdate("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, prescription_id, name, frequency, schedule, dose, unit, days) " +
            "VALUES (:id, :consultationId, :userId, :practitionerId, :prescriptionId, :name, :frequency, :schedule, :dose, :unit, :days)")
    int add(@Bind("id") String id,
            @Bind("consultationId") String consultationId,
            @Bind("userId") String userId,
            @Bind("practitionerId") String practitionerId,
            @Bind("prescriptionId") String prescriptionId,
            @Bind("name") String name,
            @Bind("frequency") Integer frequency,
            @Bind("schedule") Integer schedule,
            @Bind("dose") Integer dose,
            @Bind("unit") String unit,
            @Bind("days") Integer days);
}
