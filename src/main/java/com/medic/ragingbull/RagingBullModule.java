/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull;

import com.google.inject.AbstractModule;
import com.medic.ragingbull.jdbi.dao.*;
import com.medic.ragingbull.resources.*;
import com.medic.ragingbull.auth.UserAuthenticator;
import com.medic.ragingbull.services.*;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

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

        // Binding Authenticator
        bind(UserAuthenticator.class).asEagerSingleton();

        // Binding Database and DAO's
        bind(DBI.class).toInstance(database);
        bind(UserDao.class).toInstance(database.onDemand(UserDao.class));
        bind(InviteDao.class).toInstance(database.onDemand(InviteDao.class));
        bind(SessionDao.class).toInstance(database.onDemand(SessionDao.class));
        //bind(PharmacyDao.class).toInstance(database.onDemand(PharmacyDao.class));

        // Binding Services
        bind(UserService.class).asEagerSingleton();
        bind(AuthService.class).asEagerSingleton();
        //bind(PharmacyService.class).asEagerSingleton();

        // Binding Resources
        bind(HelloRagingBull.class).asEagerSingleton();
        bind(RegistrationResource.class).asEagerSingleton();
        bind(AuthResource.class).asEagerSingleton();
        //bind(ConsultationResource.class).asEagerSingleton();
        //bind(OrderResource.class).asEagerSingleton();
        //bind(PharmacyResource.class).asEagerSingleton();
        //bind(PractitionerResource.class).asEagerSingleton();

    }
}
