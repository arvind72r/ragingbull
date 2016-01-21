/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Address;
import com.medic.ragingbull.jdbi.mapper.AddressMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(AddressMapper.class)
public interface AddressDao {

    @SqlQuery("SELECT * from user_address where user_id = :userId")
    List<Address> getAddressBook(@Bind("userId") String userId);

    @SqlQuery("SELECT * from user_address where id = :id AND user_id = :userId")
    Address getAddress(@Bind("id") String id, @Bind("userId") String userId);

    @SqlUpdate("INSERT into user_address (id, user_id, label, address1, address2, zip, longitude, latitude) values (:id, :userId, :label, :address1, :address2, :zip, :longitude, :latitude")
    int insert(@Bind("id") String id,
            @Bind("userId") String userId,
            @Bind("label") String label,
            @Bind("address1") String address1,
            @Bind("address2") String address2,
            @Bind("zip") Integer zip,
            @Bind("longitude") Float longitude,
            @Bind("latitude") Float latitude);

    @SqlUpdate("DELETE from user_address where id = :id AND user_id = :userId")
    int delete(String userId, String addressId);
}
