/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.services;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.naked.penguin.api.Goal;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.exception.ResourceUpdateException;
import com.naked.penguin.jdbi.dao.GoalDao;
import com.naked.penguin.util.Ids;
import com.naked.penguin.util.Time;

import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
public class GoalService {

    private final GoalDao goalDao;

    @Inject
    public GoalService(GoalDao goalDao) {
        this.goalDao = goalDao;
    }
    public ImmutableList<Goal> getGoals(User user) throws ResourceFetchException {
        try {
            ImmutableList<Goal> goals = goalDao.getGoals(user.getId());
            return goals;
        } catch (Exception e) {
            throw new ResourceFetchException("Error fetching resource");
        }
    }

    public void createGoal(User user, Goal goal) throws ResourceCreationException {
        String goalId = Ids.generateId(com.naked.penguin.config.Ids.Type.GOAL);
        // Expiry of each goal is 30 days.
        long expiry = Time.getMillisAfterXDays(30);
        int isGoalCreated = goalDao.createGoal(goalId, goal.getUserId(), goal.getName(), goal.getDescription(), expiry);
        if (isGoalCreated == 0) {
            throw new ResourceCreationException("Error creating Goal");
        }

    }

    public void updateGoal(User user, Goal goal) throws ResourceUpdateException {
        int goalUpdated = goalDao.updateGoal(goal.getId(), goal.getName(), goal.getDescription());
        if (goalUpdated == 0) {
            throw new ResourceUpdateException("Error updating resource");
        }
    }
}
