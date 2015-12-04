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
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
@JsonSnakeCase
public class MailConfig {
    @JsonProperty
    @NotEmpty
    private String subject;

    @JsonProperty
    @NotEmpty
    private String fromEmail;

    @JsonProperty
    @NotEmpty
    private String fromName;

    @JsonProperty
    @NotEmpty
    private List<Map<String, String>> to;

    @JsonProperty
    @NotEmpty
    private Map<String, String> headers;

    @JsonProperty
    @NotEmpty
    private Boolean important = Boolean.FALSE;

    @JsonProperty
    @NotEmpty
    private Boolean trackOpens;

    @JsonProperty
    @NotEmpty
    private Boolean trackClicks;

    @JsonProperty
    @NotEmpty
    private Boolean merge;

    @JsonProperty
    @NotEmpty
    private String mergeLanguage;

    @JsonProperty
    @NotEmpty
    private List<MailDynamicContent> globalMergeVars;

    public MailConfig(String subject, String fromEmail, String fromName) {
        this.subject = subject;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public MailConfig(String subject, String fromEmail, String fromName, List<Map<String, String>> to, Map<String, String> headers, Boolean important, Boolean trackOpens, Boolean trackClicks, Boolean merge, String mergeLanguage, List<MailDynamicContent> globalMergeVars) {
        this.subject = subject;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.to = to;
        this.headers = headers;
        this.important = important;
        this.trackOpens = trackOpens;
        this.trackClicks = trackClicks;
        this.merge = merge;
        this.mergeLanguage = mergeLanguage;
        this.globalMergeVars = globalMergeVars;
    }

    public String getSubject() {
        return subject;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public List<Map<String, String>> getTo() {
        return to;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Boolean getImportant() {
        return important;
    }

    public Boolean getTrackOpens() {
        return trackOpens;
    }

    public Boolean getTrackClicks() {
        return trackClicks;
    }

    public Boolean getMerge() {
        return merge;
    }

    public String getMergeLanguage() {
        return mergeLanguage;
    }

    public List<MailDynamicContent> getGlobalMergeVars() {
        return globalMergeVars;
    }
}
