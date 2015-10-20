/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.dao;

import com.google.common.collect.ImmutableList;
import com.naked.penguin.api.Goal;
import com.naked.penguin.jdbi.mapper.BindTimestamp;
import com.naked.penguin.jdbi.mapper.GoalMapper;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(GoalMapper.class)
public interface GoalDao {

    @SqlUpdate("INSERT INTO goal (id, user_id, name, description, expiry) " +
            "VALUES(:id, :user_id, :name, :description, :expiry)")
    int createGoal(@Bind("id") String id, @Bind("user_id") String userId, @Bind("name") String name, @Bind("description") String description, @BindTimestamp("expiry") long expiry);

    @SqlQuery("SELECT * from goal where user_id = :userId")
    ImmutableList<Goal> getGoals(@Bind("userId") String userId);

    @SqlUpdate("UPDATE goal set name = :name, description= :description where id= :id")
    int updateGoal(@Bind("id") String id, @Bind("name") String name, @Bind("description") String description);
}
