/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Consultation;
import com.medic.ragingbull.api.Drug;
import com.medic.ragingbull.jdbi.mapper.BindDrug;
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public abstract class TransactionalDao {

    @Transaction
    public String lockConsultation(String practitionerId, String consultationId) throws TransactionException {

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

        String consultationUserId = getUserIdByConsultationId(consultationId);
        return consultationUserId;
    }

    @Transaction
    public boolean createPractitioner(String userId,
                                      Long role,
                                      String practitionerId,
                                      String description,
                                      String primaryContact,
                                      String secondaryContact,
                                      String primaryId,
                                      String secondaryId,
                                      String registrationId,
                                      String registrationAuthority,
                                      String license) {
        int practitionerCreated = createPractitioner(practitionerId, userId, description, primaryContact, secondaryContact, primaryId, secondaryId,
                registrationId, registrationAuthority, license);

        if (practitionerCreated == 0) {
            throw new TransactionException("Unable to create practitioner");
        }

        // Update user role
        int userRoleUpdated = updateRoleById(userId, role);
        if (userRoleUpdated == 0) {
            throw new TransactionException("Unable to update user role");
        }

        return true;
    }

    @Transaction
    public boolean createPractitionerLocation(String userId,
                                              Long role,
                                              String practitionerLocationId,
                                              String practitionerId,
                                              String name,
                                              String description,
                                              String speciality,
                                              String location,
                                              String primaryContact,
                                              String secondaryContact,
                                              String address1,
                                              String address2,
                                              String city,
                                              String state,
                                              Long zip,
                                              String country,
                                              String landmark,
                                              Float longitude,
                                              Float latitude,
                                              Integer workingHours,
                                              Integer workingDays,
                                              String license) {
        // Create PractitionerLocation

        int practitionerLocationCreated = createPractitionerLocation(
                practitionerLocationId, userId, practitionerId, name, description, speciality, location, primaryContact, secondaryContact,
                address1, address2, city, state, zip, country, landmark, longitude, latitude, workingHours, workingDays, license);

        if (practitionerLocationCreated == 0) {
            throw new TransactionException("Unable to create practitioner location");
        }

        // Update user role
        int userRoleUpdated = updateRoleById(userId, role);
        if (userRoleUpdated == 0) {
            throw new TransactionException("Unable to update user role");
        }

        // Update session of the user to have latest role
        int roleUpdated = updateSessionByUserId( userId, role);
        if (roleUpdated == 0) {
            throw new TransactionException("Unable to update user role");
        }

        return true;
    }

    @Transaction
    public boolean addEntityUsers(String userId, Long role, String id, String createdBy, String entityId, String entity) {

        // Create entity user
        int entityCreated = createEntityUser(id, userId, createdBy, entityId, entity);
        if (entityCreated == 0) {
            throw new TransactionException("Unable to create practitioner location");
        }

        // Update user role
        int userRoleUpdated = updateRoleById(userId, role);
        if (userRoleUpdated == 0) {
            throw new TransactionException("Unable to update user role");
        }

        // Update session of the user to have latest role
        int roleUpdated = updateSessionByUserId( userId, role);
        if (roleUpdated == 0) {
            throw new TransactionException("Unable to update user role");
        }
        return true;
    }

    @Transaction
    public Consultation getCompleteConsultation(String consultationId) {
        Consultation consultation = getConsultation(consultationId);
        return consultation;
    }

    @Transaction
    public boolean createPrescription(String prescriptionId, String consultationId, String practitionerId, String userId, List<Drug> drugs) {
        // Create prescription
        int prescriptionCreated = createPrescription(prescriptionId, consultationId, practitionerId, userId);

        if (prescriptionCreated == 0) {
            throw new TransactionException("Unable to create prescription");
        }
        // Create drugs for the prescription
        int[] drugsCreated = createDrugs(drugs);
        if (drugsCreated.length != drugs.size()) {
            throw new TransactionException("Unable to create drugs");
        }
        return true;
    }

    /*
             _____  _____ _       _____                 _
            /  ___||  _  | |     |  _  |               (_)
            \ `--. | | | | |     | | | |_   _  ___ _ __ _  ___  ___
             `--. \| | | | |     | | | | | | |/ _ \ '__| |/ _ \/ __|
            /\__/ /\ \/' / |____ \ \/' / |_| |  __/ |  | |  __/\__ \
            \____/  \_/\_\_____/  \_/\_\\__,_|\___|_|  |_|\___||___/


     */


    @SqlBatch("INSERT INTO PRESCRIPTION_DRUG (id, consultation_id, user_id, practitioner_id, prescription_id, name, frequency, schedule, dose, unit, days) " +
            "VALUES (:id, :consultationId, :userId, :practitionerId, :prescriptionId, :name, :frequency, :schedule, :dose, :unit, :days)")
    protected  abstract int[] createDrugs(@BindDrug List<Drug> drugs);

    @SqlUpdate("INSERT INTO prescription (id, consultation_id, practitioner_id, user_id ) " +
            "VALUES(:id, :consultationId, :practitionerId, :userId)")
    protected  abstract int createPrescription(@Bind("id") String id,
            @Bind("consultationId") String consultationId,
            @Bind("practitionerId") String practitionerId,
            @Bind("userId") String userId);

    @SqlQuery("")
    protected abstract Consultation getConsultation(String consultationId);

    @SqlQuery("SELECT USER_ID from CONSULTATION where id = :id")
    protected abstract String getUserIdByConsultationId(@Bind("id") String id);

    @SqlUpdate("INSERT INTO entity_users (id, user_id, created_by, entity_id, entity) VALUES (:id, :userId, :createdBy, :entityId, :entity)")
    protected abstract int createEntityUser(@Bind("id") String adminId,
                                           @Bind("userId") String userId,
                                           @Bind("createdBy") String createdBy,
                                           @Bind("entityId") String entityId,
                                           @Bind("entity") String entity);

    @SqlUpdate("UPDATE SESSIONS set role = :role where user_id = :userId")
    protected abstract int updateSessionByUserId(@Bind("userId") String userId, @Bind("role") Long role);

    @SqlUpdate("INSERT INTO practitioner_location (id, user_id, practitioner_id, name, description, speciality, " +
            "location, primary_contact, secondary_contact, address1, address2, city, state, zip, country, landmark, longitude, " +
            "latitude, working_hours, working_days, license) VALUES(:id, :userId, :practitionerId, :name," +
            ":description, :speciality, :location, :primaryContact, :secondaryContact, :address1, :address2, :city, :state, :zip, :country," +
            " :landmark, :longitude, :latitude, :workingHours, :workingDays, :license)")
    protected abstract int createPractitionerLocation(@Bind("id") String practitionerLocationId,
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

    @SqlUpdate("UPDATE users SET role = :role where id = :id")
    protected abstract int updateRoleById(@Bind("id") String id, @Bind("role") Long role);

    @SqlUpdate("INSERT INTO practitioner (id, user_id, description, primary_contact, secondary_contact, primary_id, " +
            "secondary_id, registration_id, registration_authority, license) VALUES(:id, :userId, " +
            ":description, :primaryContact, :secondaryContact, :primaryId, :secondaryId, :registrationId, " +
            ":registrationAuthority, :license)")
    protected abstract int createPractitioner(@Bind("id") String practitionerId,
                                              @Bind("userId") String userId,
                                              @Bind("description") String description,
                                              @Bind("primaryContact") String primaryContact,
                                              @Bind("secondaryContact") String secondaryContact,
                                              @Bind("primaryId") String primaryId,
                                              @Bind("secondaryId") String secondaryId,
                                              @Bind("registrationId") String registrationId,
                                              @Bind("registrationAuthority") String registrationAuthority,
                                              @Bind("license") String license);

    @SqlUpdate("UPDATE prescription SET active = false WHERE practitioner_id = :practitionerId AND consultation_id = :consultationId")
    protected abstract int lockPrescription(@Bind("practitionerId") String practitionerId, @Bind("consultationId") String consultationId);

    @SqlUpdate("UPDATE NOTES SET active = false WHERE entity_id = :entityId")
    protected abstract int lockNotes(@Bind("entityId") String entityId);

    @SqlUpdate("UPDATE CONSULTATION SET active = false WHERE id = :id AND practitioner_id = :practitionerId")
    protected abstract int lockConsultations(@Bind("id") String id, @Bind("practitionerId") String practitionerId);



}
