/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.core.services.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vamshi Molleti
 */
@Path("/upload")
@Produces(MediaType.APPLICATION_JSON)
public class UploadResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadResource.class);

    private UploadService uploadService;

    @Inject
    public UploadResource(UploadService uploadService) {
        this.uploadService = uploadService;
    }


}
