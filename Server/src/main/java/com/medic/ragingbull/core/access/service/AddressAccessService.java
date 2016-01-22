/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.service;

import com.google.inject.Inject;
import com.medic.ragingbull.api.Address;
import com.medic.ragingbull.api.AddressResponse;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.core.services.AddressService;
import com.medic.ragingbull.exception.StorageException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class AddressAccessService {

    private AddressService addressService;

    @Inject
    public AddressAccessService(AddressService addressService) {
        this.addressService = addressService;
    }

    public Response getAddressBook(Session session, String userId) {
        List<AddressResponse> response = addressService.getAddressBook(session, userId);
        return Response.ok().entity(response).build();
    }

    public Response getAddress(Session session, String userId, String addressId) {
        AddressResponse response = addressService.getAddress(session, userId, addressId);
        return Response.ok().entity(response).build();
    }

    public Response addAddress(Session session, String userId, Address address) throws StorageException {
        AddressResponse response = addressService.addAddress(session, userId, address);
        return Response.ok().entity(response).build();
    }

    public Response deleteAddress(Session session, String userId, String addressId) throws StorageException {
        boolean success = addressService.deleteAddress(session, userId, addressId);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}
