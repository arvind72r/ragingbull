/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Practitioner;
import com.medic.ragingbull.jdbi.mapper.PractitionerMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PractitionerMapper.class)
public interface PractitionerDao {

    @SqlUpdate("INSERT INTO practitioner (id, user_id, description, primary_contact, secondary_contact, primary_id, " +
            "secondary_id, registration_id, registration_authority, license, verified, active) VALUES(:id, :userId, " +
            ":description, :primaryContact, :secondaryContact, :primaryId, :secondaryId, :registrationId, " +
            ":registrationAuthority, :license, :verified, :active)")
    int createPractitioner(@Bind("id") String practitionerId,
                           @Bind("userId") String userId,
                           @Bind("description") String description,
                           @Bind("primaryContact") String primaryContact,
                           @Bind("secondaryContact") String secondaryContact,
                           @Bind("primaryId") String primaryId,
                           @Bind("secondaryId") String secondaryId,
                           @Bind("registrationId") String registrationId,
                           @Bind("registrationAuthority") String registrationAuthority,
                           @Bind("license") String license,
                           @Bind("verified") Boolean verified,
                           @Bind("active") Boolean active);

    @SqlQuery("SELECT * FROM practitioner where id = :id")
    Practitioner getPractitionerById(@Bind("id") String id);
}
