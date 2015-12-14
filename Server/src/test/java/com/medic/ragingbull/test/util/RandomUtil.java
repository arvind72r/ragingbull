/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;

/**
 * Created by Vamshi Molleti
 */
public class RandomUtil {


    public static String randomString(String base) {
        return String.format(base, Math.random());
    }
}
