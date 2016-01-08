/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Layout;
import com.cwbase.logback.RedisAppender;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Optional;
import io.dropwizard.logging.AbstractAppenderFactory;
import io.dropwizard.validation.PortRange;
import org.hibernate.validator.constraints.NotEmpty;
import redis.clients.jedis.Protocol;

import java.net.InetAddress;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vamshi Molleti
 */
@JsonTypeName("redis")
public class RedisAppenderFactory extends AbstractAppenderFactory {
    @JsonProperty
    private int callerStackIndex = 0; // Logger call stack to avoid log wrappers introduced by some frameworks

    @JsonProperty
    private int database = Protocol.DEFAULT_DATABASE; // redis database protocol

    @JsonProperty
    @NotEmpty
    private String host = "localhost"; // redis host

    @JsonProperty
    @NotEmpty
    private String key = "logstash"; // redis namespace

    @JsonProperty
    private boolean location = false; // enable Location Context which includes class, method, file, and line

    @JsonProperty
    private boolean mdc = true; // enable Mapped Diagnostic Context http://logback.qos.ch/manual/mdc.html

    @JsonProperty
    @NotEmpty
    private String name = "redis"; // logger name

    @JsonProperty
    private Optional<String> password = Optional.absent(); // redis password

    @JsonProperty
    @PortRange(min = 1)
    private int port = Protocol.DEFAULT_PORT; // redis port

    @JsonProperty
    @NotEmpty
    private String sourceEnv = "dev"; // the environment that is logging

    @JsonProperty
    private Optional<String> sourceHost = getHostName(); // the host that is logging

    @JsonProperty
    private Optional<String> sourcePath = Optional.absent(); // the path to the file containing the same logs

    @JsonProperty
    private Optional<String> tags = Optional.absent(); // comma separated

    @JsonProperty
    private int timeout = Protocol.DEFAULT_TIMEOUT; // redis timeout

    @JsonProperty
    @NotEmpty
    private String sourceType = "ragingbull-api"; // the application that is logging

    @Override
    public Appender<ILoggingEvent> build(LoggerContext context, String applicationName, Layout<ILoggingEvent> layout) {
        checkNotNull(context);

        final RedisAppender appender = new RedisAppender();

        appender.setCallerStackIndex(callerStackIndex);
        appender.setContext(context);
        appender.setDatabase(database);
        appender.setHost(host);
        appender.setKey(key);
        appender.setLocation(location);
        appender.setMdc(mdc);
        appender.setName(name);
        appender.setPassword(password.orNull());
        appender.setPort(port);
        appender.setSource(sourceEnv);
        appender.setSourceHost(sourceHost.orNull());
        appender.setSourcePath(sourcePath.orNull());
        appender.setTags(tags.orNull());
        appender.setTimeout(timeout);
        appender.setType(sourceType);

        addThresholdFilter(appender, threshold);
        appender.start();

        return wrapAsync(appender);
    }

    public static Optional<String> getHostName() {
        try {
            return Optional.of(InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            return Optional.absent();
        }
    }
}
