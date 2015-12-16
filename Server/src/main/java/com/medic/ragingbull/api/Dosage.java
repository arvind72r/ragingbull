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

    public enum Schedule {
        MORNING(512), AFTERNOON(64), EVENING(8), NIGHT(1);
        private Integer bitValue;

        Schedule(Integer i) {
            this.bitValue = i;
        }

        public Integer getBitValue() {
            return bitValue;
        }
    }


}
