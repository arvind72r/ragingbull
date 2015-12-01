/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.ImageService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Vamshi Molleti
 */
@Path("/images")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResource.class);

    private ImageService imageService;

    @Inject
    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @POST
    @Path("/entity/{id}")
    public Response createImage(@Auth Session session, @PathParam("entityId") String entityId, byte[] fileBytes) throws IOException {
        return imageService.createImage(session, entityId, fileBytes);
    }

    @GET
    @Path("/entity/{id}/images")
    public Response getImages(@Auth Session session, @PathParam("entityId") String entityId) throws IOException {
        return imageService.getImages(session, entityId);
    }


    @GET
    @Path("/{id}")
    public Response getImage(@Auth Session session, @PathParam("id") String id) {

        return imageService.getImage(session, id);
    }

}
