/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.config;

/**
 * Created by Vamshi Molleti
 */
public class Ids {

    public enum Type {
        SESSION("ss"), USER ("us"), CONSULTATION("cn"), PHARMACY("phm"), PRACTITIONER("pr"), INVITE("in");
        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
