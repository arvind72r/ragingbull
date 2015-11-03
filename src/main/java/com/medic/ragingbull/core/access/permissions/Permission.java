/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.permissions;

/**
 * Created by Vamshi Molleti
 */
public enum Permission{

    READ(1), WRITE(2), MODIFY(4), DELETE(8);
    private int BIT_VALUE;

    Permission(int bitValue) {
        this.BIT_VALUE = bitValue;
    }

    public int getBitValue() {
        return BIT_VALUE;
    }
}