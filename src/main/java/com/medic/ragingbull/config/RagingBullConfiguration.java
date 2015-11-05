/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
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
    @JsonProperty("twilio")
    private TwilioConfiguration twilioConfiguration = new TwilioConfiguration();

    @Valid
    @NotNull
    @JsonProperty("google")
    private GoogleConfiguration googleConfiguration = new GoogleConfiguration();

    public GoogleConfiguration getGoogleConfiguration() {
        return googleConfiguration;
    }
    public TwilioConfiguration getTwilioConfiguration() {
        return twilioConfiguration;
    }


}
