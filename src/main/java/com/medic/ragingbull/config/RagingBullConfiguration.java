/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class RagingBullConfiguration extends Configuration
{
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
    @JsonProperty("twilio")
    private TwilioConfiguration twilioConfiguration = new TwilioConfiguration();

    @Valid
    @NotNull
    @JsonProperty("google")
    private GoogleConfiguration googleConfiguration = new GoogleConfiguration();

    @Valid
    @NotNull
    @JsonProperty("facebook")
    private FacebookConfiguration facebookConfiguration = new FacebookConfiguration();


    public GoogleConfiguration getGoogleConfiguration() {
        return googleConfiguration;
    }
    public TwilioConfiguration getTwilioConfiguration() {
        return twilioConfiguration;
    }

    public FacebookConfiguration getFacebookConfiguration() {
        return facebookConfiguration;
    }
}
