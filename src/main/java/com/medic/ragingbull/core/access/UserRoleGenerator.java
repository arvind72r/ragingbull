/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class UserRoleGenerator {

    public Long generateRole (Long currentRole , UserRoles.Permissions... permissions) {
        for (UserRoles.Permissions permission : permissions) {
            currentRole = currentRole | permission.getBitValue();
        }
        return currentRole;
    }

    public Long generateRole (Long currentRole , List<UserRoles.Permissions> permissions) {
        for (UserRoles.Permissions permission : permissions) {
            currentRole = currentRole | permission.getBitValue();
        }
        return currentRole;
    }
}
