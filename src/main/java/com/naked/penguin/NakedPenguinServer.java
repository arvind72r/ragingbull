/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.naked.penguin.api.User;
import com.naked.penguin.auth.UserAuthenticator;
import com.naked.penguin.config.DbMigrateOnStartupBundle;
import com.naked.penguin.resources.*;
import com.naked.penguin.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
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
public class NakedPenguinServer extends Application<NakedPenguinConfiguration> {

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new NakedPenguinServer().run(args);
    }

    public Injector createInjector(Module module) {
        return Guice.createInjector(module);
    }
    @Override
    public void initialize(Bootstrap<NakedPenguinConfiguration> bootstrap) {
        // Set up the migration bundle to manage the databases
        bootstrap.addBundle(new MigrationsBundle<NakedPenguinConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(NakedPenguinConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        // Set up the DBI Exceptions bundle
        bootstrap.addBundle(new DBIExceptionsBundle());

        // Set up the db migrate bundle to migrate on startup
        bootstrap.addBundle(new DbMigrateOnStartupBundle());
    }

    @Override
    public void run(NakedPenguinConfiguration configuration, Environment environment) throws Exception {

        final DBI jdbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "database");

        // Initialize Guice
        final NakedPenguinModule newSendItAPIModule = new NakedPenguinModule(configuration, environment, jdbi);
        injector = createInjector(newSendItAPIModule);

        // Registering Authenticator
        //environment.jersey().register(injector.getInstance(UserAuthenticator.class));
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<User>(new UserAuthenticator(injector.getInstance(UserService.class)), "", User.class)));

        //Registering Resources
        environment.jersey().register(injector.getInstance(HelloNakedPenguin.class));
        environment.jersey().register(injector.getInstance(RegistrationResource.class));
        environment.jersey().register(injector.getInstance(InvitationResource.class));
        environment.jersey().register(injector.getInstance(GoalResource.class));
        environment.jersey().register(injector.getInstance(CompanyResource.class));
        environment.jersey().register(injector.getInstance(OrganizationResource.class));

        //Registering Services
        //environment.jersey().register(injector.getInstance(UserService.class));
    }


}
