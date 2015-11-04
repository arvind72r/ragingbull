/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.auth.SessionAuthFactory;
import com.medic.ragingbull.core.auth.SessionAuthenticator;
import com.medic.ragingbull.config.DbMigrateOnStartupBundle;
import com.medic.ragingbull.core.providers.Authorization;
import com.medic.ragingbull.resources.*;
import com.medic.ragingbull.core.auth.UserAuthenticator;
import com.medic.ragingbull.core.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.ChainedAuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Vamshi Molleti
 */
public class RagingBullServer extends Application<RagingBullConfiguration> {

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new RagingBullServer().run(args);
    }

    public Injector createInjector(Module module) {
        return Guice.createInjector(module);
    }

    @Override
    public void initialize(Bootstrap<RagingBullConfiguration> bootstrap) {
        // Set up the migration bundle to manage the databases
        bootstrap.addBundle(new MigrationsBundle<RagingBullConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RagingBullConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        // Set up the DBI Exceptions bundle
        bootstrap.addBundle(new DBIExceptionsBundle());

        // Set up the db migrate bundle to migrate on startup
        bootstrap.addBundle(new DbMigrateOnStartupBundle());
    }

    @Override
    public void run(RagingBullConfiguration configuration, Environment environment) throws Exception {

        final DBI jdbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "database");

        // Initialize Guice
        final RagingBullModule newSendItAPIModule = new RagingBullModule(configuration, environment, jdbi);
        injector = createInjector(newSendItAPIModule);

        // Registering Authenticator
        ChainedAuthFactory<Session> chainedFactory = new ChainedAuthFactory<>(
                new BasicAuthFactory<Session>(new UserAuthenticator(injector.getInstance(UserService.class)), "", Session.class),
                new SessionAuthFactory<Session>(new SessionAuthenticator())
        );

        environment.jersey().register(AuthFactory.binder(chainedFactory));

        // Registering providers
        //environment.jersey().register(injector.getInstance(Authorization.class));

        //Registering Resources
        environment.jersey().register(injector.getInstance(HelloRagingBull.class));
        environment.jersey().register(injector.getInstance(RegistrationResource.class));
        environment.jersey().register(injector.getInstance(AuthResource.class));
        //environment.jersey().register(injector.getInstance(PractitionerResource.class));
        //environment.jersey().register(injector.getInstance(PractitionerLocationResource.class));
    }


}
