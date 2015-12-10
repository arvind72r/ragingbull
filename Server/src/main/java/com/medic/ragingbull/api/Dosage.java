/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class Dosage {

    public enum Regularity {
        MORNING(512l), AFTERNOON(64l), EVENING(8l), NIGHT(1l);
        private Long bitValue;

        Regularity(Long i) {
            this.bitValue = i;
        }

        public Long getBitValue() {
            return bitValue;
        }
    }


}
