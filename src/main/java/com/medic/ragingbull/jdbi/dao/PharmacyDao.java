/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Pharmacy;
import com.medic.ragingbull.jdbi.mapper.PharmacyMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(PharmacyMapper.class)
public interface PharmacyDao {

    @SqlUpdate("INSERT INTO pharmacy (id, name, contact_name, location, primary_contact, secondary_contact, address1, " +
            "address2, city, state, zip, landmark, longitude, latitude, delivery_radius, working_hours, working_days, " +
            "license, verified, active) VALUES(:id, :name, :contactName, :location, :primaryContact, :secondaryContact," +
            " :address1, :address2, :city, :state, :zip, :country, :landmark, :longitude, :latitude, :deliveryRadius," +
            " :workingHours, :workingDays, :isVerified, :isActive, :licenseDoc)")
    int createPharmacy(@Bind("id") String pharmacyId,
                       @Bind("name") String name,
                       @Bind("contactName") String contactName,
                       @Bind("location") String location,
                       @Bind("primaryContact") Integer primaryContact,
                       @Bind("secondaryContact") Integer secondaryContact,
                       @Bind("address1") String address1,
                       @Bind("address2") String address2,
                       @Bind("city") String city,
                       @Bind("state") String state,
                       @Bind("zip") Long zip,
                       @Bind("country") String country,
                       @Bind("landmark") String landmark,
                       @Bind("longitude") Float longitude,
                       @Bind("latitude") Float latitude,
                       @Bind("deliveryRadius") Integer deliveryRadius,
                       @Bind("workingHours") Integer workingHours,
                       @Bind("workingDays") Integer workingDays,
                       @Bind("isVerified") Boolean isVerified,
                       @Bind("isActive") Boolean isActive,
                       @Bind("licenseDoc") String licenseDoc);

    @SqlQuery("SELECT * FROM pharmacy where name = :name")
    Pharmacy getByEmail(@Bind("name") String name);

    @SqlQuery("SELECT * FROM pharmacy where id = :id")
    Pharmacy getById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM pharmacy where primary_contact = :primary_contact")
    Pharmacy getByPrimaryContact(@Bind("primary_contact") String primaryContact);
}
