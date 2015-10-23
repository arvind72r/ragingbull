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
public interface SystemContants {

    DateTime EXPIRY_TIME = new DateTime().plus(Days.ONE);
}
