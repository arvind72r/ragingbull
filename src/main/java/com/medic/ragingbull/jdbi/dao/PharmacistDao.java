/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Pharmacist;
import com.medic.ragingbull.api.PharmacyBack;
import com.medic.ragingbull.jdbi.mapper.PharmacistMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PharmacistMapper.class)
public interface PharmacistDao {

    @SqlUpdate("INSERT INTO pharmacist (id, user_id, description, primary_contact, secondary_contact, primary_id, " +
            "secondary_id, registration_id, registration_authority, license) VALUES(:id, :userId, " +
            ":description, :primaryContact, :secondaryContact, :primaryId, :secondaryId, :registrationId, " +
            ":registrationAuthority, :license)")
    int createPharmacist(@Bind("id") String practitionerId,
                         @Bind("userId") String userId,
                         @Bind("description") String description,
                         @Bind("primaryContact") String primaryContact,
                         @Bind("secondaryContact") String secondaryContact,
                         @Bind("primaryId") String primaryId,
                         @Bind("secondaryId") String secondaryId,
                         @Bind("registrationId") String registrationId,
                         @Bind("registrationAuthority") String registrationAuthority,
                         @Bind("license") String license);

    @SqlQuery("SELECT * FROM pharmacist where name = :name")
    PharmacyBack getByEmail(@Bind("name") String name);

    @SqlQuery("SELECT * FROM pharmacist where id = :id")
    PharmacyBack getById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM pharmacist where primary_contact = :primary_contact")
    PharmacyBack getByPrimaryContact(@Bind("primary_contact") String primaryContact);

    @SqlQuery("SELECT * FROM pharmacist where user_id = :userId")
    Pharmacist getByUserId(@Bind("userId") String userId);
}
