/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.permissions;

/**
 * Created by Vamshi Molleti
 */
public enum Privileges {

    READ(1), WRITE(2), MODIFY(4), DELETE(8), READ_OTHERS(16), WRITE_OTHERS(32), MODIFY_OTHERS(64), DELETE_OTHERS(128);
    private int BIT_VALUE;

    Privileges(int bitValue) {
        this.BIT_VALUE = bitValue;
    }

    public int getBitValue() {
        return BIT_VALUE;
    }
}