/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Image;
import com.medic.ragingbull.api.ImageResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.ImagesConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.ImagesDao;
import com.medic.ragingbull.jdbi.dao.TransactionalDao;
import com.medic.ragingbull.resources.ImageResource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    private ImagesConfiguration imagesConfig;
    private TransactionalDao transactionalDao;
    private ImagesDao imagesDao;

    @Inject
    public ImageService(RagingBullConfiguration configuration, ImagesDao imagesDao, TransactionalDao transactionalDao) {
        this.imagesConfig = configuration.getImagesConfiguration();
        this.imagesDao = imagesDao;
        this.transactionalDao = transactionalDao;

        new File(imagesConfig.getPath()).mkdir();
    }

    public ImageResponse createImage(Session session, String userId, ImageResource.ImageEntity entityType, String encodedImage) throws ResourceCreationException, StorageException {

        try {
            String imageId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.IMAGE);
            if (entityType == ImageResource.ImageEntity.userImage) {
                // Update user image url with newly created url
                File imageFile = new File(imagesConfig.getPath() + userId + "/profile/profilePic.jpeg");
                byte[] bytes = Base64.decodeBase64(encodedImage);
                FileUtils.writeByteArrayToFile(imageFile, bytes);

                boolean createdProfile = transactionalDao.addUserProfileImage(imageId, userId, entityType.name(), imageFile.getAbsolutePath());

                if (!createdProfile) {
                    LOGGER.error(String.format("Error creating profile image user %s. DB failed to save the record", session.getUserEmail()));
                    throw new StorageException("Error creating user profile. Please try again");
                }

            } else if (entityType == ImageResource.ImageEntity.prescription) {
                // Link userId with the prescriptionId
                File prescriptionFile = new File(imagesConfig.getPath() + userId + "/prescription/" + DateTime.now().getMillis() + ".jpeg");
                byte[] bytes = Base64.decodeBase64(encodedImage);
                FileUtils.writeByteArrayToFile(prescriptionFile, bytes);
                imagesDao.createImage(imageId, userId, entityType.name(), prescriptionFile.getAbsolutePath());
            }
            return new ImageResponse(imageId, entityType.name());

        } catch(StorageException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public List<ImageResponse> getImages(Session session, String userId) {
        List<ImageResponse> responses = new ArrayList<>();
        List<Image> images = imagesDao.getByUserId(userId);
        for (Image image : images) {
            responses.add(new ImageResponse(image.getId(), image.getType()));
        }
        return responses;
    }

    public ImageResponse getImage(Session session, String userId, String imageId) throws ResourceCreationException, StorageException {
        try {
            Image image = imagesDao.getById(imageId, userId);
            if (image == null) {
                LOGGER.error(String.format("Error fetching image user %s. Image: %s DB failed to fetch the record", session.getUserEmail(), imageId));
                throw new StorageException("Error fetching user image. Please try again");
            }
            // Fetch the image and encode to base64

            File file = new File(image.getPath());
            if (file.exists()) {
                byte[] fileBytes = FileUtils.readFileToByteArray(file);
                String content = Base64.encodeBase64String(fileBytes);
                return new ImageResponse(imageId, image.getType(), content);

            }
            return null;
        } catch(StorageException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceCreationException(e.getMessage());
        }


    }

    public List<ImageResponse> getPrescriptionImages(Session session, String userId) {
        List<ImageResponse> responses = new ArrayList<>();
        List<Image> images = imagesDao.getPrescriptionByUserId(userId);
        for (Image image : images) {
            responses.add(new ImageResponse(image.getId(), image.getType()));
        }
        return responses;
    }
}
