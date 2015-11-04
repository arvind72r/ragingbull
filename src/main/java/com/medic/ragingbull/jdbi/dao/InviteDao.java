/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;


import com.medic.ragingbull.api.Invite;
import com.medic.ragingbull.jdbi.mapper.BindTimeStamp;
import com.medic.ragingbull.jdbi.mapper.InviteMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(InviteMapper.class)
public interface InviteDao {
    @SqlUpdate("INSERT INTO invite (id, user_id, code, expiry) " +
            "VALUES(:id, :user_id, :code, :expiry)")
    int createInvite(@Bind("id") String id,
                     @Bind("user_id") String user_id,
                     @Bind("code") Integer code,
                     @BindTimeStamp("expiry") long expiry);

    @SqlQuery("SELECT * FROM invite where id = :id")
    Invite getInviteById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM invite where user_id = :user_id")
    List<Invite> getInviteByEmail(@Bind("user_id") String user_Id);

    @SqlQuery("SELECT * FROM invite where user_id = :user_id and availed = false")
    Invite getInviteByUserId(@Bind("user_id") String userId);
    @SqlQuery("SELECT * FROM invite where code = :code")
    Invite getInviteByCode(@Bind("code") String code);
}