/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Address;
import com.medic.ragingbull.api.AddressResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.exception.StorageException;
import com.medic.ragingbull.jdbi.dao.AddressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class AddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    private AddressDao addressDao;

    @Inject
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public List<AddressResponse> getAddressBook(Session session, String userId) {
        List<AddressResponse> responses = new ArrayList<>();
        List<Address> addressList = addressDao.getAddressBook(userId);
        for (Address address : addressList) {
            AddressResponse response = new AddressResponse(address.getId(), address.getUserId(), address.getLabel(), address.getAddress1(), address.getAddress2(), address.getZip(), address.getLongitude(), address.getLatitude());
            responses.add(response);
        }
        return responses;
    }

    public AddressResponse getAddress(Session session, String userId, String addressId) {
        Address address = addressDao.getAddress(addressId, userId);
        AddressResponse response = new AddressResponse(address.getId(), address.getUserId(), address.getLabel(), address.getAddress1(), address.getAddress2(), address.getZip(), address.getLongitude(), address.getLatitude());
        return response;
    }

    public AddressResponse addAddress(Session session, String userId, Address address) throws StorageException {
        try {
            address.setId(com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER_ADDRESS));
            int addressAdded = addressDao.insert(address.getId(), userId, address.getLabel(), address.getAddress1(), address.getAddress2(), address.getZip(), address.getLongitude(), address.getLatitude());

            if (addressAdded == 0) {
                LOGGER.error(String.format("Error adding address for user %s. DB failed to save the record", session.getUserEmail()));
                throw new StorageException("Error adding user address. Please try again");
            }

            AddressResponse response = new AddressResponse(address.getId(), userId, address.getLabel(), address.getAddress1(), address.getAddress2(), address.getZip(), address.getLongitude(), address.getLatitude());
            return response;
        } catch(Exception e) {
            LOGGER.error(String.format("Error adding user address %s. Exception %s", session.getUserEmail(), e));
            throw e;
        }

    }

    public boolean deleteAddress(Session session, String userId, String addressId) throws StorageException {
        try {
            int addressDeleted = addressDao.delete(addressId, userId);
            if (addressDeleted == 0) {
                LOGGER.error(String.format("Error deleting address for user %s. DB failed to delete the record", session.getUserEmail()));
                throw new StorageException("Error deleting user address. Please try again");
            }

            return true;
        } catch(Exception e) {
            LOGGER.error(String.format("Error adding user address %s. Exception %s", session.getUserEmail(), e));
            throw e;
        }
    }
}
