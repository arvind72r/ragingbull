/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.roles;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class UserRoleGenerator {

    public static UserRoles.Role generateRole (Long currentRole) {
        UserRoles.Role generatedRole = null;
        for (UserRoles.Role role : UserRoles.Role.values()) {
            if ( role.getRoleBit() >= currentRole && (role.getRoleBit() & currentRole) == role.getRoleBit()) {
                generatedRole = role;
            }
        }
        return generatedRole;
    }

    public Long generateRole (Long currentRole , List<UserRoles.Permissions> permissions) {
        for (UserRoles.Permissions permission : permissions) {
            currentRole = currentRole | permission.getBitValue();
        }
        return currentRole;
    }
}
