/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.EntityUser;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.PractitionerService;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class PractitionerAccessService {

    private PractitionerService practitionerService;

    @Inject
    public PractitionerAccessService(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    public Response getPractitioners(Session session) throws StorageException {
        List<Map<String, Object>> responseList = practitionerService.getPractitioners();
        return Response.ok().entity(responseList).build();
    }
}
