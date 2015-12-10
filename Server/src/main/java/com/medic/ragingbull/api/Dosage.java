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
        MORNING(512), AFTERNOON(64), EVENING(8), NIGHT(1);
        private int bitValue;

        Regularity(int i) {
            this.bitValue = i;
        }

        public int getBitValue() {
            return bitValue;
        }
    }


}
