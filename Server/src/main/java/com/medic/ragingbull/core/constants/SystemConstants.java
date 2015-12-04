/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.constants;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Vamshi Molleti
 */
public interface SystemConstants {

    // Entities in system.
    enum Entities {
        USER, PHARMACIST, PRACTITIONER, PHARMACY_LOCATION, PRACTITIONER_LOCATION, CONSULTATION, PRESCRIPTION, ORDER
    }

    enum NotesTypes {
        SYMPTOMS, DIAGNOSIS, USER, FEEDBACK
    }

    enum AccessEntities{
        PASSWORD, INVITE
    }

    enum Sex {MALE, FEMALE}

    DateTime EXPIRY_TIME = new DateTime().plus(Days.ONE);

    Integer DEFAULT_DELIVERY_RADIUS = 3;
    Integer DEFAULT_WORKING_HOURS = 12;
    Integer DEFAULT_WORKING_DAYS = 7;

    // COOKIE NAME
    String SESSION_COOKIE_NAME = "Auth-Token";

    // NOTIFICATIONS
    String SMS_REGISTRATION_TOKEN = "Ahoy from RagingBull!!! Enter %s on the sign up page to verify your account. This is a one time message.";

    // RANDOMS
    String MEMBER_DEFAULT_PASSWORD = "new_password";
    Integer MAX_BOUND = 100000;

    // OAuthResources
    String FACEBOOK_GRAPH_API_URL = "https://graph.facebook.com/v2.5/";
}
