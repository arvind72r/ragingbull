/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access;

import com.medic.ragingbull.core.access.roles.Role;

/**
 * Created by Vamshi Molleti
 */
public class RoleGenerator {

    public static int generateRole(Role... roles) {
        if (roles != null && roles.length > 0) {
            int ROLE_BIT_VALUE = 0;
            for (Role role : roles) {
                ROLE_BIT_VALUE = ROLE_BIT_VALUE | role.getBitValue();
            }
            return ROLE_BIT_VALUE;
        }
        return -1;
    }

}
