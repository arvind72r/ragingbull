/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.ImageResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.ErrorMessages;
import com.medic.ragingbull.core.services.ImageService;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.resources.ImageResource;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class ImageAccessService {

    private ImageService imageService;

    @Inject
    public ImageAccessService(ImageService imageService) {
        this.imageService = imageService;
    }

    public Response createImage(Session session, String userId, ImageResource.ImageEntity entity, String encodedImage) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            ImageResponse response = imageService.createImage(session, userId, entity, encodedImage);
            return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getImages(Session session, String userId) {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            List<ImageResponse> images = imageService.getImages(session, userId);
            return Response.ok().entity(images).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }

    public Response getImage(Session session, String userId, String imageId) throws ResourceCreationException, StorageException {
        if ((session.getRole() & UserRoles.Permissions.BLOCK.getBitValue()) != UserRoles.Permissions.BLOCK.getBitValue()) {
            ImageResponse image = imageService.getImage(session, userId, imageId);
            if (image == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().entity(image).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(ErrorMessages.FORBIDDEN_READ_MEMBER_CODE).build();
    }
}
