/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.medic.ragingbull.core.access.roles.UserRoles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class DashBoard {

    private Map<UserRoles.Role, List<Object>> dash;

    public DashBoard() {};

    public DashBoard(Map<UserRoles.Role, List<Object>> dash) {
        this.dash = dash;
    }

    public Map<UserRoles.Role, List<Object>> getDash() {
        return dash;
    }

    public List<Object> getRoleResources(UserRoles.Role role) {
        if (!dash.containsKey(role)) {
            return dash.put(role, new ArrayList<>());
        }
        return dash.get(role);
    }
}
