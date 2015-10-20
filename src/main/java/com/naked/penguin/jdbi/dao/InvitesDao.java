/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.naked.penguin.api.Invite;
import com.naked.penguin.jdbi.mapper.BindTimestamp;
import com.naked.penguin.jdbi.mapper.InviteMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(InviteMapper.class)
public interface InvitesDao {
    @SqlUpdate("INSERT INTO invites (id, user_id, expiry) " +
            "VALUES(:id, :user_id, :expiry)")
    int createInvite(@Bind("id") String id,
                   @Bind("user_id") String user_id,
                   @BindTimestamp("expiry") long expiry);

    @SqlQuery("SELECT * FROM invites where id = :id")
    Invite getInviteById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM invites where user_id = :user_id")
    Invite getInviteByEmail(@Bind("user_id") String user_Id);
}
