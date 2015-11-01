/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.jdbi.mapper.ConsultationMapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(ConsultationMapper.class)
public interface ConsultationDao  {
    Consultation getConsultation(String practitionerId, String locationId, String consultId);

    int createConsultation(String consultationId, String practitionerId, String locationId, String userId, String name, Integer slot, String notes);
}
