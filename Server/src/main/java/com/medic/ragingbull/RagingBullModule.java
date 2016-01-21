/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull;

import com.google.inject.AbstractModule;
import com.medic.ragingbull.api.PractitionerLocation;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.access.roles.UserRoleGenerator;
import com.medic.ragingbull.core.access.service.*;
import com.medic.ragingbull.core.auth.UserAuthenticator;
import com.medic.ragingbull.core.providers.Authorization;
import com.medic.ragingbull.core.services.*;
import com.medic.ragingbull.jdbi.dao.*;
import com.medic.ragingbull.resources.*;
import com.medic.ragingbull.task.GenerateSampleDataTask;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
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
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build("Sample Application");

        bind(Client.class).toInstance(client);
        // Binding Authenticator
        bind(UserAuthenticator.class).asEagerSingleton();

        // Binding Independent classes
        bind(UserRoleGenerator.class).asEagerSingleton();

        // Binding Tasks
        bind(GenerateSampleDataTask.class).asEagerSingleton();
        // Binding Database and DAO's
        bind(DBI.class).toInstance(database);
        bind(UsersDao.class).toInstance(database.onDemand(UsersDao.class));
        bind(AccessDao.class).toInstance(database.onDemand(AccessDao.class));
        bind(SessionsDao.class).toInstance(database.onDemand(SessionsDao.class));
        bind(OAuthDao.class).toInstance(database.onDemand(OAuthDao.class));
        bind(PractitionerDao.class).toInstance(database.onDemand(PractitionerDao.class));
        bind(PharmacistDao.class).toInstance(database.onDemand(PharmacistDao.class));
        bind(PractitionerLocationDao.class).toInstance(database.onDemand(PractitionerLocationDao.class));
        bind(PharmacyLocationDao.class).toInstance(database.onDemand(PharmacyLocationDao.class));
        bind(EntityUsersDao.class).toInstance(database.onDemand(EntityUsersDao.class));
        bind(ImagesDao.class).toInstance(database.onDemand(ImagesDao.class));
        bind(ConsultationDao.class).toInstance(database.onDemand(ConsultationDao.class));
        bind(NotesDao.class).toInstance(database.onDemand(NotesDao.class));
        bind(DrugsDao.class).toInstance(database.onDemand(DrugsDao.class));
        bind(PrescriptionDao.class).toInstance(database.onDemand(PrescriptionDao.class));
        bind(TransactionalDao.class).toInstance(database.onDemand(TransactionalDao.class));
        bind(AddressDao.class).toInstance(database.onDemand(AddressDao.class));


        // Binding Services
        bind(UserService.class).asEagerSingleton();
        bind(AuthService.class).asEagerSingleton();
        bind(OAuthService.class).asEagerSingleton();
        bind(PractitionerService.class).asEagerSingleton();
        bind(PharmacyService.class).asEagerSingleton();
        bind(PractitionerLocationService.class).asEagerSingleton();
        bind(ImageService.class).asEagerSingleton();
        bind(ConsultationService.class).asEagerSingleton();
        bind(PrescriptionService.class).asEagerSingleton();
        bind(PrescriptionService.class).asEagerSingleton();
        bind(AddressService.class).asEagerSingleton();

        //Access Service
        bind(UserAccessService.class).asEagerSingleton();
        bind(PractitionerAccessService.class).asEagerSingleton();
        bind(PractitionerLocationAccessService.class).asEagerSingleton();
        bind(ConsultationAccessService.class).asEagerSingleton();
        bind(PrescriptionAccessService.class).asEagerSingleton();
        bind(AddressAccessService.class).asEagerSingleton();


        // Registering providers
        bind(Authorization.class).asEagerSingleton();

        // Binding Resources
        bind(HelloRagingBull.class).asEagerSingleton();
        bind(RegistrationResource.class).asEagerSingleton();
        bind(AuthResource.class).asEagerSingleton();
        bind(UserResource.class).asEagerSingleton();
        bind(OAuthResource.class).asEagerSingleton();
        bind(PractitionerResource.class).asEagerSingleton();
        bind(PharmacistResource.class).asEagerSingleton();
        bind(PractitionerLocationResource.class).asEagerSingleton();
        bind(PharmacyLocationResource.class).asEagerSingleton();
        bind(ImageResource.class).asEagerSingleton();
        bind(ConsultationResource.class).asEagerSingleton();
        bind(PrescriptionResource.class).asEagerSingleton();
        bind(AddressResource.class).asEagerSingleton();


    }
}
