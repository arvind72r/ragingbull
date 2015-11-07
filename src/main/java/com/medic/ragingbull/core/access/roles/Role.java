/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.roles;

/**
 * Created by Vamshi Molleti
 */
public enum Role {
    PSEUDO_ADMIN(1), NATIVE_USER(2), PRACTITIONER(3), PHARMACIST(4), ANON(5) ;

    private int BIT_VALUE;

    Role(int bitValue) {
        this.BIT_VALUE = bitValue;
    }

    public int getBitValue() {
        return BIT_VALUE;
    }
}
