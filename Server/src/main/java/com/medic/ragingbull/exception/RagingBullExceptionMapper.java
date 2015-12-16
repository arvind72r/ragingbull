/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.exception;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Vamshi Molleti
 */
@Provider
@Singleton
public class RagingBullExceptionMapper implements ExceptionMapper<RagingBullBaseException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RagingBullExceptionMapper.class);

    @Override
    public Response toResponse(RagingBullBaseException exception) {

        if (exception instanceof DuplicateEntityException) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug("Caught unexpected exception", exception);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
