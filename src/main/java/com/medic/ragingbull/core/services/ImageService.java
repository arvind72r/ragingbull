/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.ImagesConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.jdbi.dao.ImagesDao;
import com.medic.ragingbull.jdbi.dao.UserDao;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Created by Vamshi Molleti
 */
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    private ImagesConfiguration imagesConfig;
    private ImagesDao imagesDao;
    private UserDao userDao;

    @Inject
    public ImageService(RagingBullConfiguration configuration, ImagesDao imagesDao, UserDao userDao) {
        this.imagesConfig = configuration.getImagesConfiguration();
        this.imagesDao = imagesDao;
        this.userDao = userDao;

        new File(imagesConfig.getPath()).mkdir();
    }

    public  Response createImage(Session session, String entityId, byte[] fileBytes) {

        try {
            boolean userProfile = false;
            if (StringUtils.equals(entityId, "me")) {
                // Call to update user profile image
                entityId = session.getUserId();
                userProfile = true;
            }
            File imageFile = new File("/Users/vamshi/RagingBullImages/"+ entityId + "/" + new DateTime().getMillis());
            FileUtils.writeByteArrayToFile(imageFile, fileBytes);

            // Make an entry in DB
            String imageId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.IMAGE);
            int imageCreated = imagesDao.createImage(imageId, entityId, imageFile.getAbsolutePath());

            if (imageCreated == 0) {
                // Throw resource creation exception
            }

            if (userProfile) {
                // Update the profile url in user table;
                int pictureUrlUpdated = userDao.pictureUrl(session.getUserId(), imageFile.getAbsolutePath());
                if (pictureUrlUpdated == 0) {
                    // Throw resource creation exception
                }
            }
            return Response.ok().build();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    public Response getImages(Session session, String entityId) {
        return null;
    }

    public Response getImage(Session session, String id) {
        return null;
    }
}
