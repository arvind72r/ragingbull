/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.config;

import com.codahale.metrics.MetricRegistry;
import com.naked.penguin.NakedPenguinConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.migrations.CloseableLiquibase;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import liquibase.Contexts;
import liquibase.changelog.ChangeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class DbMigrateOnStartupBundle implements ConfiguredBundle<NakedPenguinConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbMigrateOnStartupBundle.class);

    @Override
    public final void initialize(Bootstrap<?> bootstrap) {
    }

    /**
     * Migrate the DB using liquibase.
     * <p/>
     * Ported from DbMigrateCommand.
     *
     * @param configuration the configuration object
     * @param environment   the application's {@link Environment}
     * @throws Exception if something goes wrong
     * @see io.dropwizard.migrations.DbMigrateCommand#run(io.dropwizard.setup.Bootstrap, net.sourceforge.argparse4j.inf.Namespace, io.dropwizard.Configuration)
     * @see io.dropwizard.migrations.DbMigrateCommand#run(net.sourceforge.argparse4j.inf.Namespace, liquibase.Liquibase)
     */
    @Override
    public void run(NakedPenguinConfiguration configuration, Environment environment) throws Exception {

        final DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();

        final ManagedDataSource dataSource = dataSourceFactory.build(new MetricRegistry(), "liquibase");

        try (final CloseableLiquibase liquibase = new CloseableLiquibase(dataSource)) {
            final Contexts contexts = new Contexts();
            if (configuration.isDbMigrateOnStartup()) {
                // migrate the db before starting up the app
                liquibase.update(contexts);
            } else {
                // If not migrating the db on startup, then fail if not all change sets have been applied
                final List<ChangeSet> changeSetsToRun = liquibase.listUnrunChangeSets(contexts);
                final ArrayList<ChangeSet> changeSetsToRunExcludingAlwaysRun = new ArrayList<>();
                for (final ChangeSet changeSet : changeSetsToRun) {
                    // Liquibase.listUnrunChangeSets returns all change sets to run instead of just new change sets.
                    // If nothing has changed, this would still include change sets configured to always run.
                    // The list should therefore only contain change sets that must run on change and new change sets.
                    if (!changeSet.isAlwaysRun()) {
                        changeSetsToRunExcludingAlwaysRun.add(changeSet);
                    }
                }
                if (!changeSetsToRunExcludingAlwaysRun.isEmpty()) {
                    final String errorMessage = String.format(
                            "%d change sets may need to applied to %s@%s%n" +
                                    "Run 'db status --verbose' for details.%n" +
                                    "Run 'db migrate' to update the schema.",
                            changeSetsToRun.size(),
                            liquibase.getDatabase().getConnection().getConnectionUserName(),
                            liquibase.getDatabase().getConnection().getURL());
                    LOGGER.error(errorMessage);
                    System.err.println(errorMessage);
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            final String errorMessage = String.format(
                    "%s failed.%n" +
                            "You may need to run 'db drop' followed by 'db migrate' to recreate the schema%n%s",
                    configuration.isDbMigrateOnStartup() ? "db migrate" : "db validate",
                    e.getMessage());
            LOGGER.error(errorMessage);
            System.err.println(errorMessage);
            System.exit(1);
        }
    }
}
