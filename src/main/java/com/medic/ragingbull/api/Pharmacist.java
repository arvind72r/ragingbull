/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pharmacist extends Entity {

    public Pharmacist() {
    }

    public Pharmacist(String id, String userId, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.registrationId = registrationId;
        this.registrationAuthority = registrationAuthority;
        this.license = license;
    }

    public Pharmacist(String id, String userId, String description, String primaryContact, String secondaryContact, String primaryId, String secondaryId, String registrationId, String registrationAuthority, String license, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.registrationId = registrationId;
        this.registrationAuthority = registrationAuthority;
        this.license = license;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
