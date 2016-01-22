/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.resources;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Image;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.service.ImageAccessService;
import com.medic.ragingbull.core.services.ImageService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Vamshi Molleti
 */
@Path("/user/{id}/image")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResource.class);
    public enum ImageEntity{ userImage, prescription}
    private ImageAccessService imageAccessService;


    @Inject
    public ImageResource(ImageAccessService imageAccessService) {
        this.imageAccessService = imageAccessService;
    }

    @POST
    public Response createImage(@Auth Session session, @PathParam("id") String userId, @QueryParam("type") ImageEntity entity, String encodedImage) throws ResourceCreationException, StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return imageAccessService.createImage(session, userId, entity, encodedImage);
    }

    @GET
    public Response getImage(@Auth Session session, @PathParam("id") String userId) {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return imageAccessService.getImages(session, userId);
    }

    @GET
    @Path("/{imageId}")
    public Response getImages(@Auth Session session, @PathParam("id") String userId, @PathParam("imageId") String imageId) throws IOException, ResourceCreationException, StorageException {
        if (StringUtils.equalsIgnoreCase(userId, "me")) {
            userId = session.getUserId();
        }
        return imageAccessService.getImage(session, userId, imageId);
    }
}
