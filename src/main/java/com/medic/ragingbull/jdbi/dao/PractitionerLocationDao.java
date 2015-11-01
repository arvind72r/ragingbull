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

    @SqlUpdate("INSERT INTO practitioner_location (id, practitioner_id, name, contact_name, description, discipline, " +
            "location, primary_contact, secondary_contact, address1, city, state, zip, country, landmark, longitude, " +
            "latitude, working_hours, working_days, license, verified, active) VALUES(:id, :userId, " +
            ":description, :primaryContact, :secondaryContact, :address1, :address2, :city, :state, :zip, :country," +
            " :landmark, :longitude, :latitude, :working_hours, :working_days, :license, :verified, :active)")
    int createPractitionerLocation(@Bind("id") String practitionerLocationId,
                                   @Bind("practitioner_id") String practitionerId,
                                   @Bind("id") String name,
                                   @Bind("contact_name") String contactName,
                                   @Bind("description") String description,
                                   @Bind("description") String discipline,
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
                                   @Bind("working_hours") Integer workingHours,
                                   @Bind("working_days") Integer workingDays,
                                   @Bind("license") String license,
                                   @Bind("verified") Boolean isVerified,
                                   @Bind("active") Boolean isActive);

    @SqlQuery("SELECT * FROM practitioner_location where id = :id")
    PractitionerLocation getPractitionerLocation(String locationId);



}
