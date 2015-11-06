/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.core.constants;

/**
 * Created by Vamshi Molleti
 */
public class Ids {

    public enum Type {
        SESSION("ss"), RESET("rs"), USER ("us"), CONSULTATION("cn"), PHARMACY("phm"), PRACTITIONER("pr"), INVITE("in"), PRACTITIONER_LOCATION("pr-lc"), PRESCRIPTION("ps"), OAUTH("oa");
        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
