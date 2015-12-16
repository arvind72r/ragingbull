/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class RagingBullConfiguration extends Configuration {
    @Valid
    @JsonProperty
    boolean dbMigrateOnStartup;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    public boolean isDbMigrateOnStartup() {
        return dbMigrateOnStartup;
    }

    @Valid
    @NotNull
    @JsonProperty("httpClient")
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty("jerseyClient")
    private JerseyClientConfiguration jersyClient = new JerseyClientConfiguration();

    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClient;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jersyClient;
    }


    @Valid
    @NotNull
    @JsonProperty("google")
    private GoogleConfiguration googleConfiguration = new GoogleConfiguration();

    @Valid
    @NotNull
    @JsonProperty("facebook")
    private FacebookConfiguration facebookConfiguration = new FacebookConfiguration();

    @Valid
    @NotNull
    @JsonProperty("notifications")
    NotificationConfiguration notificationConfiguration = new NotificationConfiguration();
    @Valid
    @NotNull
    @JsonProperty("images")
    private ImagesConfiguration imagesConfiguration = new ImagesConfiguration();

    @Valid
    @NotNull
    @JsonProperty("task")
    private TaskConfiguration taskConfiguration = new TaskConfiguration();

    public GoogleConfiguration getGoogleConfiguration() {
        return googleConfiguration;
    }

    public FacebookConfiguration getFacebookConfiguration() {
        return facebookConfiguration;
    }

    public NotificationConfiguration getNotificationConfiguration() {
        return notificationConfiguration;
    }

    public ImagesConfiguration getImagesConfiguration() {
        return imagesConfiguration;
    }

    public TaskConfiguration getTaskConfiguration() {
        return taskConfiguration;
    }
}
