/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.config;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Vamshi Molleti
 */
public interface SystemConstants {

    DateTime EXPIRY_TIME = new DateTime().plus(Days.ONE);

    Integer DEFAULT_DELIVERY_RADIUS = 3;
    Integer DEFAULT_WORKING_HOURS = 12;
    Integer DEFAULT_WORKING_DAYS = 7;

    // COOKIE NAME
    String SESSION_COOKIE_NAME = "Auth-Token";

}
