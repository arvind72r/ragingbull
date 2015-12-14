/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.base.Optional;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Optional.of;

/**
 * Created by Vamshi Molleti
 */
public class ClientUtil {

    // Default values
    private static Optional<MediaType> DEFAULT_TYPE = Optional.absent();

    private static String buildUrl(String url, Object[] params) {
        if (params == null || params.length == 0) {
            return url;
        }
        return String.format(url, params);
    }

    public static Response postRequest(String url, Optional<MultivaluedMap<String,String>> queryParam, Object postData, Object... pathParams) {
        return getWebResource(Optional.absent(), url, queryParam, DEFAULT_TYPE, pathParams).post(Entity.entity(postData, MediaType.APPLICATION_JSON));
    }

    public static Response postRequestWithAuth(String authToken, String url, Optional<MultivaluedMap<String,String>> queryParam, Object postData, Object... pathParams) {
        return getWebResource(of(authToken), url, queryParam, DEFAULT_TYPE, pathParams).post(Entity.entity(postData, MediaType.APPLICATION_JSON));
    }



    private static Client getClient() {
        Client client = ClientBuilder.newClient();

        // Create your own Jackson ObjectMapper.
        ObjectMapper mapper = new ObjectMapper();
        // Register the joda module
        mapper.registerModule(new JodaModule());
        // Register the guava module
        mapper.registerModule(new GuavaModule());
        // Create your own JacksonJaxbJsonProvider and then assign it to the config.
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(mapper);

        client.register(jacksonProvider);

        return client;
    }

    private static Invocation.Builder getWebResource(Optional<String> authToken,
                                                     String url,
                                                     Optional<MultivaluedMap<String, String>> queryParams,
                                                     Optional<MediaType> mediaType,
                                                     Object... pathParams) {
        final Client client = getClient();

        WebTarget target = client.target(
                buildUrl(url, pathParams));

        if (queryParams.isPresent()) {
            for (final String queryKey : queryParams.get().keySet()) {
                List<String> values = queryParams.get().get(queryKey);
                for (final String value : values) {
                    target = target.queryParam(queryKey, value);
                }
            }
        }
        final MediaType type = mediaType.isPresent() ? mediaType.get() : MediaType.APPLICATION_JSON_TYPE;

        Invocation.Builder builder = target.request(type);


        if (authToken.isPresent()) {
            builder.header(TestConstants.HEADER_AUTH, authToken.get());
        }

        return builder;
    }
}
