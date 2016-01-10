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
import com.medic.ragingbull.api.User;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
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

    public static Response putRequestWithAuth(String authToken, String url, Optional<MultivaluedMap<String,String>> queryParams, Object postData, Object... pathParams) {
        return getWebResource(of(authToken), url, queryParams, DEFAULT_TYPE, pathParams).put(Entity.entity(postData, MediaType.APPLICATION_JSON));
    }

    public static Response getRequestWithAuth(String authToken, String url, Optional<MultivaluedMap<String,String>> queryParams, Object... pathParams) {
        return getWebResource(of(authToken), url, queryParams, DEFAULT_TYPE, pathParams).get();
    }

    public static Response getRequestWithBasicAuth(String userName, String password, String url, Optional<MultivaluedMap<String,String>> queryParams, Object... pathParams) {
        return getWebResourceBasicAuth(of(userName), of(password), url, queryParams, DEFAULT_TYPE, pathParams).get();
    }

    public static Response postRequestBasicAuth(String userName, String password, String url,  Optional<MultivaluedMap<String,String>> queryParam, Object postData, Object... pathParams) {
        return getWebResourceBasicAuth(of(userName), of(password), url, queryParam, DEFAULT_TYPE, pathParams).post(Entity.entity(postData, MediaType.APPLICATION_JSON));
    }

    public static Response postRequestBasicAuthFormData(String userName, String password, String url, Optional<MultivaluedMap<String,String>> queryParams, Optional<MultivaluedMap<String, String>> formParams, Object... pathParams) {
        return getWebResourceBasicAuth(of(userName), of(password), url, queryParams, of(MediaType.APPLICATION_FORM_URLENCODED_TYPE), pathParams).accept(MediaType.MEDIA_TYPE_WILDCARD).post(Entity.entity(formParams.get(), MediaType.APPLICATION_FORM_URLENCODED));
    }
    public static Response deleteRequestWithAuth(String authToken, String url, Optional<MultivaluedMap<String,String>> queryParams, Object... pathParams) {
        return getWebResource(of(authToken), url, queryParams, DEFAULT_TYPE, pathParams).delete();
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

    private static Invocation.Builder getWebResourceBasicAuth(Optional<String> userName, Optional<String> password,
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


        if (userName.isPresent() && password.isPresent()) {
            String encoding = null;
            try {
                encoding = DatatypeConverter.printBase64Binary((userName.get() + ":" + password.get()).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            builder.header(TestConstants.HEADER_BASIC_AUTH, "Basic "+encoding);
        }

        return builder;
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
