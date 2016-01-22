/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.resources.ImageResource;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private String url;

    @JsonProperty
    private String data;

    public ImageResponse(String id, String type) {
        this.id = id;
        if (StringUtils.equals(type, ImageResource.ImageEntity.prescription.name())) {
            url = "/user/me/image/" + id;
        }
    }

    public ImageResponse(String id, String url, String data) {
        this.id = id;
        this.url = "/user/me/image/" + id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }
}
