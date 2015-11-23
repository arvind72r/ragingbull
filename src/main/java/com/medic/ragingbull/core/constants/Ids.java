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
        RESET("rs"),
        USER ("us"),
        ANON_USER("au"),
        CONSULTATION("cn"),
        PHARMACY("ph"),
        PHARMACY_LOCATION("ph-lc"),
        PRACTITIONER("pr"),
        INVITE("in"),
        PRACTITIONER_LOCATION("pr-lc"),
        PRESCRIPTION("ps"),
        OAUTH("oa"),
        ENTITY_ADMIN("ea"),
        IMAGE("im"),
        NOTES("nt");
        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    public enum Notes{
        DIAGNOSIS, SYMPTOMS, USER, FEEDBACK
    }
}
