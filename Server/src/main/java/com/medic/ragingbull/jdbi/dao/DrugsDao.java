/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.jdbi.mapper.BindDrug;
import com.medic.ragingbull.jdbi.mapper.DrugsMapper;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(DrugsMapper.class)
public interface DrugsDao {

    @SqlBatch("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, name, manufacturer, quantity, frequency, allergies) " +
            "VALUES (:id, :consultationId, :practitionerId, :userId, :name, :manufacturer, :quantity, :frequency, :allergies)")
    int createAll(@BindDrug("drug") List<Drug> drugs);
}
