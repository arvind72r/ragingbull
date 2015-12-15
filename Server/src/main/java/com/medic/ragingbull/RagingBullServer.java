/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.config.DbMigrateOnStartupBundle;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.auth.SessionAuthFactory;
import com.medic.ragingbull.core.auth.SessionAuthenticator;
import com.medic.ragingbull.core.auth.UserAuthenticator;
import com.medic.ragingbull.core.services.UserService;
import com.medic.ragingbull.resources.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.ChainedAuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.math.BigInteger;
import java.util.EnumSet;

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

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.PREFLIGHT_MAX_AGE_PARAM, "5184000"); // 2 month
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        filter.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM, "Location,Content-Disposition,Content-Length,Content-Range");

        // Handle the mappers
        SimpleModule toStringModule = new SimpleModule();
        toStringModule.addSerializer(BigInteger.class, new ToStringSerializer());
        toStringModule.addSerializer(Long.class, new ToStringSerializer());
        environment.getObjectMapper().registerModule(toStringModule);

        // Registering Authenticator
        ChainedAuthFactory<Session> chainedFactory = new ChainedAuthFactory<>(
                new BasicAuthFactory<>(new UserAuthenticator(injector.getInstance(UserService.class)), "", Session.class),
                new SessionAuthFactory<Session>(new SessionAuthenticator(injector.getInstance(UserService.class)))
        );

        environment.jersey().register(AuthFactory.binder(chainedFactory));

        //Registering Resources
        environment.jersey().register(injector.getInstance(HelloRagingBull.class));
        environment.jersey().register(injector.getInstance(RegistrationResource.class));
        environment.jersey().register(injector.getInstance(AuthResource.class));
        environment.jersey().register(injector.getInstance(UserResource.class));
        environment.jersey().register(injector.getInstance(OAuthResource.class));
        environment.jersey().register(injector.getInstance(PractitionerResource.class));
        environment.jersey().register(injector.getInstance(PharmacistResource.class));
        environment.jersey().register(injector.getInstance(PharmacyLocationResource.class));
        environment.jersey().register(injector.getInstance(PractitionerLocationResource.class));
        environment.jersey().register(injector.getInstance(ImageResource.class));
        environment.jersey().register(injector.getInstance(ConsultationResource.class));
    }
    public <S> S getService(Class<S> serviceClass) {
        return injector.getInstance(serviceClass);
    }

    public <S> S getService(Class<S> serviceClass, String name) {
        return injector.getInstance(Key.get(serviceClass, Names.named(name)));
    }



}
