/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.resources;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.naked.penguin.api.Goal;
import com.naked.penguin.api.User;
import com.naked.penguin.exception.ResourceCreationException;
import com.naked.penguin.exception.ResourceFetchException;
import com.naked.penguin.exception.ResourceUpdateException;
import com.naked.penguin.services.GoalService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vamshi Molleti
 */
@Path("/goals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class GoalResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationResource.class);
    private final GoalService goalService;

    @Inject
    public GoalResource(GoalService goalService) {
        this.goalService = goalService;
    }

    @GET
    public Response getGoal(@Auth User user) throws ResourceFetchException {
        ImmutableList<Goal> goals = goalService.getGoals(user);
        return Response.ok().entity(goals.asList()).build();
    }

    @POST
    public Response createGoal(@Auth User user, Goal goal) throws ResourceCreationException {
        goalService.createGoal(user, goal);
        return Response.ok().build();
    }

    @PUT
    public Response updateGoal(@Auth User user, Goal goal) throws ResourceUpdateException {
        goalService.updateGoal(user, goal);
        return Response.ok().build();
    }
}
