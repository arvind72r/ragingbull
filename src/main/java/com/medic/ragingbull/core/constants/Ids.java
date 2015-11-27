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
        SESSION("ss"),
        USER ("us"),
        OAUTH("oa"),
        CONSULTATION("cn"),
        PHARMACY("ph"),
        PHARMACY_LOCATION("ph-lc"),
        PRACTITIONER("pr"),
        PRACTITIONER_LOCATION("pr-lc"),
        PRESCRIPTION("ps"),
        DRUG("dg"),
        ENTITY_ADMIN("ea"),
        IMAGE("im"),
        NOTES("nt"),
        ACCESS("ac"),
        INVITE("in"),
        RESET("rs");
        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

}
