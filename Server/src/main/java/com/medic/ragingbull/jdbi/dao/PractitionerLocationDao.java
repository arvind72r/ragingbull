/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.jdbi.mapper.PractitionerLocationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PractitionerLocationMapper.class)
public interface PractitionerLocationDao {

    @SqlUpdate("INSERT INTO practitioner_location (id, user_id, practitioner_id, name, description, speciality, " +
            "location, primary_contact, secondary_contact, address1, address2, city, state, zip, country, landmark, longitude, " +
            "latitude, working_hours, working_days, license) VALUES(:id, :userId, :practitionerId, :name," +
            ":description, :speciality, :location, :primaryContact, :secondaryContact, :address1, :address2, :city, :state, :zip, :country," +
            " :landmark, :longitude, :latitude, :workingHours, :workingDays, :license)")
    int createPractitionerLocation(@Bind("id") String practitionerLocationId,
                                   @Bind("userId") String userId,
                                   @Bind("practitionerId") String practitionerId,
                                   @Bind("name") String name,
                                   @Bind("description") String description,
                                   @Bind("speciality") String speciality,
                                   @Bind("location") String location,
                                   @Bind("primaryContact") String primaryContact,
                                   @Bind("secondaryContact") String secondaryContact,
                                   @Bind("address1") String address1,
                                   @Bind("address2") String address2,
                                   @Bind("city") String city,
                                   @Bind("state") String state,
                                   @Bind("zip") Long zip,
                                   @Bind("country") String country,
                                   @Bind("landmark") String landmark,
                                   @Bind("longitude") Float longitude,
                                   @Bind("latitude") Float latitude,
                                   @Bind("workingHours") Integer workingHours,
                                   @Bind("workingDays") Integer workingDays,
                                   @Bind("license") String license);

    @SqlQuery("SELECT * FROM practitioner_location where id = :id")
    PractitionerLocation getPractitionerLocation(@Bind("id") String id);



}
