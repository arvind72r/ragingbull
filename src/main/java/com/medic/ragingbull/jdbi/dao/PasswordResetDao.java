/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by Vamshi Molleti
 */
public interface PasswordResetDao {


    @SqlUpdate("INSERT INTO password_reset (id, email, expiry) values (:id, :email, :expiry)")
    int resetPassword(@Bind("id") String id,
                      @Bind("email") String email,
                      @BindTimeStamp("expiry") long expiry);

    @SqlQuery("SELECT id FROM password_reset WHERE id = :id and email = :email")
    String getResetLink(@Bind("id") String id,
                        @Bind("email") String email,
                        @BindTimeStamp("expiry") long expiry);
}
