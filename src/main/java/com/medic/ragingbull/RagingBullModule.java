/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull;

import com.google.inject.AbstractModule;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.auth.UserAuthenticator;
import com.medic.ragingbull.core.providers.Authorization;
import com.medic.ragingbull.core.services.*;
import com.medic.ragingbull.jdbi.dao.*;
import com.medic.ragingbull.resources.*;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.client.Client;

/**
 * Created by Vamshi Molleti
 */
public class RagingBullModule extends AbstractModule {

    final RagingBullConfiguration configuration;
    final Environment environment;
    final DBI database;

    public RagingBullModule(final RagingBullConfiguration configuration, final Environment environment, final DBI database) {
        this.configuration = configuration;
        this.environment = environment;
        this.database = database;
    }

    @Override
    protected void configure() {
        // Disallow circular dependencies
        binder().disableCircularProxies();

        // Binding Configs
        bind(RagingBullConfiguration.class).toInstance(configuration);

        // Register HTTP Client
        //final HttpClient httpClient = new HttpClientBuilder(environment).using(configuration.getHttpClientConfiguration()).build("Sample Application");

        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build("Sample Application");

        //bind(HttpClient.class).toInstance(httpClient);
        bind(Client.class).toInstance(client);
        // Binding Authenticator
        bind(UserAuthenticator.class).asEagerSingleton();

        // Binding Database and DAO's
        bind(DBI.class).toInstance(database);
        bind(UserDao.class).toInstance(database.onDemand(UserDao.class));
        bind(PasswordResetDao.class).toInstance(database.onDemand(PasswordResetDao.class));
        bind(InviteDao.class).toInstance(database.onDemand(InviteDao.class));
        bind(SessionDao.class).toInstance(database.onDemand(SessionDao.class));
        bind(OAuthDao.class).toInstance(database.onDemand(OAuthDao.class));
        bind(PractitionerDao.class).toInstance(database.onDemand(PractitionerDao.class));
        bind(PharmacistDao.class).toInstance(database.onDemand(PharmacistDao.class));

        bind(PractitionerLocationDao.class).toInstance(database.onDemand(PractitionerLocationDao.class));

        // Binding Services
        bind(UserService.class).asEagerSingleton();
        bind(AuthService.class).asEagerSingleton();
        bind(OAuthService.class).asEagerSingleton();
        bind(PractitionerService.class).asEagerSingleton();
        bind(PractitionerLocationService.class).asEagerSingleton();

        // Registering providers
        bind(Authorization.class).asEagerSingleton();

        // Binding Resources
        bind(HelloRagingBull.class).asEagerSingleton();
        bind(RegistrationResource.class).asEagerSingleton();
        bind(AuthResource.class).asEagerSingleton();
        bind(UserResource.class).asEagerSingleton();
        bind(OAuthResource.class).asEagerSingleton();
        bind(PractitionerResource.class).asEagerSingleton();
        bind(PractitionerLocationResource.class).asEagerSingleton();


    }
}
