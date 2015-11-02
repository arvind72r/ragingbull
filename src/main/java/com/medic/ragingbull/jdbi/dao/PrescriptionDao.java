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

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public interface PrescriptionDao {


    @SqlQuery("SELECT * FROM prescription where id = :id")
    Prescription getPrescription(String prescriptionId);

    @SqlBatch("INSERT INTO prescription (id, practitionerId, locationId, location, consultId, drug_name, " +
            "drug_manufacturer, drug_quantity, drug_frequency, drug_allergies) " +
            "VALUES(:id, :practitionerId, :locationId, :consultId, :drug.name, drug.manufacturer, drug.quantity, drug.frequencyBit, drug.allergies)")
    int createPrescription(@Bind("id") String id,
                           @Bind("practitionerId") String practitionerId,
                           @Bind("locationId") String locationId,
                           @Bind("consultId") String consultId,
                           @Bind("drug") List<Drug> drugs);
}
