/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonSnakeCase
public class Mail {

    @JsonProperty
    @NotEmpty
    private String key;

    @JsonProperty
    @NotEmpty
    private String templateName;

    @JsonProperty
    @NotEmpty
    private String aync;

    @JsonProperty
    @NotEmpty
    private List<MailDynamicContent> templateContent;

    @JsonProperty
    @NotEmpty
    private MailConfig message;


    public Mail(String key, String templateName, String aync, List<MailDynamicContent> templateContent, MailConfig message) {
        this.key = key;
        this.templateName = templateName;
        this.aync = aync;
        this.templateContent = templateContent;
        this.message = message;
    }
}
