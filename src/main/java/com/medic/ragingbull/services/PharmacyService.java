/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Pharmacy;
import com.medic.ragingbull.api.PharmacyResponse;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.ErrorMessages;
import com.medic.ragingbull.config.Ids;
import com.medic.ragingbull.config.SystemConstants;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.exception.ResourceFetchException;
import com.medic.ragingbull.jdbi.dao.PharmacyDao;
import org.apache.http.HttpStatus;
import org.slf4j.LoggerFactory;

/**
 * Created by Vamshi Molleti
 */
public class PharmacyService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PharmacyService.class);
    private final PharmacyDao pharmacyDao;

    @Inject
    public PharmacyService(PharmacyDao pharmacyDao) {
        this.pharmacyDao = pharmacyDao;
    }

    public PharmacyResponse createPharmacy(User user, Pharmacy pharmacy, Boolean includeDetails) throws ResourceCreationException {

        try {
            String pharmacyId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.PHARMACY);
            String name = pharmacy.getName();
            String contactName = pharmacy.getContactName();
            String location = pharmacy.getLocation();
            Integer primaryContact = pharmacy.getPrimaryContact();
            Integer secondaryContact = pharmacy.getSecondaryContact() == null ? primaryContact: pharmacy.getSecondaryContact();
            String address1 = pharmacy.getAddress1();
            String address2 = pharmacy.getAddress2();
            String city = pharmacy.getCity();
            String state = pharmacy.getState();
            Long zip = pharmacy.getZip();
            String country = pharmacy.getCountry();
            String landmark = pharmacy.getLandmark();
            Float longitude = pharmacy.getLongitude();
            Float latitude = pharmacy.getLatitude();
            Integer deliveryRadius = pharmacy.getDeliveryRadius() == null ? SystemConstants.DEFAULT_DELIVERY_RADIUS : pharmacy.getDeliveryRadius();
            Integer workingHours = pharmacy.getWorkingHours() == null ? SystemConstants.DEFAULT_WORKING_HOURS : pharmacy.getWorkingHours();
            Integer workingDays = pharmacy.getWorkingDays() == null ? SystemConstants.DEFAULT_WORKING_DAYS : pharmacy.getWorkingDays();
            Boolean isVerified = Boolean.FALSE;
            Boolean isActive = Boolean.FALSE;
            String licenseDoc  = pharmacy.getLicenseDoc();

            // Setting generatedId
            pharmacy.setId(pharmacyId);
            pharmacy.setSecondaryContact(secondaryContact);
            pharmacy.setDeliveryRadius(deliveryRadius);
            pharmacy.setWorkingHours(workingHours);
            pharmacy.setWorkingDays(workingDays);
            pharmacy.setIsActive(isActive);
            pharmacy.setIsVerified(isVerified);

            int pharmacyCreated = pharmacyDao.createPharmacy(pharmacyId, name, contactName, location, primaryContact, secondaryContact, address1, address2, city, state, zip, country, landmark,longitude, latitude, deliveryRadius, workingHours, workingDays, isVerified, isActive, licenseDoc);

            if (pharmacyCreated == 0) {
                // Error creating pharmacy
                PharmacyResponse response = new PharmacyResponse();
                response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                response.setErrorMessage(ErrorMessages.RESOURCE_CREATION_ERROR);
                return response;

            } else {
                PharmacyResponse response = new PharmacyResponse(pharmacy, includeDetails);
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }

        } catch(Exception e) {
            LOGGER.error(String.format("Error creating pharmacy name: %s, landmark: %s. Request by: %s. Error: %s", pharmacy.getName(), pharmacy.getLandmark(), user.getName(), e));
            throw new ResourceCreationException(e.getMessage());
        }
    }

    public PharmacyResponse getPharmacy(User user, String pharmacyId) throws ResourceFetchException {
        try {
            Pharmacy pharmacy = pharmacyDao.getById(pharmacyId);
            if (pharmacy == null) {
                PharmacyResponse response = new PharmacyResponse();
                response.setStatus(HttpStatus.SC_NOT_FOUND);
                response.setErrorMessage(ErrorMessages.RESOURCE_NOT_FOUND);
                return response;
            } else {
                PharmacyResponse response = new PharmacyResponse(pharmacy, false);
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }
        } catch(Exception e) {
            LOGGER.error(String.format("Error fetching pharmacy id: %s. Request by: %s. Error: %s", pharmacyId, user.getName(), e));
            throw new ResourceFetchException(e.getMessage());
        }
    }
}
