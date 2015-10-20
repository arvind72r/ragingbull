/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin;

import com.google.inject.AbstractModule;
import com.naked.penguin.auth.UserAuthenticator;
import com.naked.penguin.jdbi.dao.*;
import com.naked.penguin.resources.*;
import com.naked.penguin.services.*;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Vamshi Molleti
 */
public class NakedPenguinModule extends AbstractModule {

    final NakedPenguinConfiguration configuration;
    final Environment environment;
    final DBI database;

    public NakedPenguinModule(final NakedPenguinConfiguration configuration, final Environment environment, final DBI database) {
        this.configuration = configuration;
        this.environment = environment;
        this.database = database;
    }

    @Override
    protected void configure() {
        // Disallow circular dependencies
        binder().disableCircularProxies();

        // Binding Configs
        bind(NakedPenguinConfiguration.class).toInstance(configuration);

        // Binding Authenticator
        bind(UserAuthenticator.class).asEagerSingleton();

        // Binding Database and DAO's
        bind(DBI.class).toInstance(database);
        bind(UserDao.class).toInstance(database.onDemand(UserDao.class));
        bind(InvitesDao.class).toInstance(database.onDemand(InvitesDao.class));
        bind(GoalDao.class).toInstance(database.onDemand(GoalDao.class));
        bind(SessionDao.class).toInstance(database.onDemand(SessionDao.class));
        bind(CompanyDao.class).toInstance(database.onDemand(CompanyDao.class));
        bind(OrganizationDao.class).toInstance(database.onDemand(OrganizationDao.class));

        // Binding Services
        bind(UserService.class).asEagerSingleton();
        bind(AuthService.class).asEagerSingleton();
        bind(GoalService.class).asEagerSingleton();
        bind(CompanyService.class).asEagerSingleton();
        bind(OrganizationService.class).asEagerSingleton();

        // Binding Resources
        bind(HelloNakedPenguin.class).asEagerSingleton();
        bind(RegistrationResource.class).asEagerSingleton();
        bind(InvitationResource.class).asEagerSingleton();
        bind(GoalResource.class).asEagerSingleton();
        bind(CompanyResource.class).asEagerSingleton();
        bind(OrganizationResource.class).asEagerSingleton();

    }
}
