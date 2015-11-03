/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.util;

import java.util.UUID;

/**
 * Created by Vamshi Molleti
 */
public class Ids {
    public static String generateId(com.medic.ragingbull.core.constants.Ids.Type type) {
        return String.format("%s-%s", type.getPrefix(), UUID.randomUUID().toString());
    }
}
