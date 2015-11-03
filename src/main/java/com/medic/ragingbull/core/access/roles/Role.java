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
    PSEUDO_ADMIN(512), NATIVE_USER(1024), PRACTITIONER(2048), PHARMACIST(4096),  ;

    private int BIT_VALUE;

    Role(int bitValue) {
        this.BIT_VALUE = bitValue;
    }

    public int getBitValue() {
        return BIT_VALUE;
    }
}
