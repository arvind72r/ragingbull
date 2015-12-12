/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;

/**
 * Created by Vamshi Molleti
 */
public abstract class TransactionalDao {

    @Transaction
    public boolean lockConsultation(String practitionerId, String consultationId) throws TransactionException{

        // LockConsultation
        int consultationLocked = lockConsultations(consultationId, practitionerId);

        if (consultationLocked == 0) {
            throw new TransactionException("Unable to lock consultation");
        }

        // Lock Notes for consultation
        int consultationNotesLocked = lockNotes(consultationId);

        if (consultationNotesLocked == 0) {
            throw new TransactionException("Unable to lock consultation notes");
        }

        // LockPrescription
        int prescriptionLocked = lockPrescription(practitionerId, consultationId);

        if (prescriptionLocked == 0) {
            throw new TransactionException("Unable to lock prescription");
        }
        return true;
    }

    @SqlUpdate("UPDATE prescription SET active = false WHERE practitioner_id = :practitionerId AND consultation_id = :consultationId")
    protected abstract int lockPrescription(@Bind("practitionerId") String practitionerId, @Bind("consultationId") String consultationId);

    @SqlUpdate("UPDATE NOTES SET active = false WHERE entity_id = :entityId")
    protected abstract int lockNotes(@Bind("entityId") String entityId);

    @SqlUpdate("UPDATE CONSULTATION SET active = false WHERE id = :id AND practitioner_id = :practitionerId")
    protected abstract int lockConsultations(@Bind("id") String id, @Bind("practitionerId") String practitionerId);

}
