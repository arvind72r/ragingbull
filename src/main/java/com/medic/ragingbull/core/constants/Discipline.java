/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.constants;

/**
 * Created by Vamshi Molleti
 */
public class Discipline {

    public enum Type {
        GENERAL("gr"), CARDIO("cr");
        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
