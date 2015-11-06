/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by Vamshi Molleti
 */
public interface OAuthDao {

    @SqlUpdate("INSERT INTO OAUTH (id, user_id, token, refresh_token, provider) values (:id, :userId, :token, :refreshToken, :provider)")
    int create(@Bind("id") String id,
               @Bind("userId") String userId,
               @Bind("token") String token,
               @Bind("refreshToken") String refreshToken,
               @Bind("provider") String provider);
}
