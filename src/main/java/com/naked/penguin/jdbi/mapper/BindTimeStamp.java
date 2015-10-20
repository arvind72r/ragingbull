/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.jdbi.mapper;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.sql.Timestamp;

/**
 * Created by Vamshi Molleti
 */
@BindingAnnotation(BindTimestamp.TimestampBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTimestamp {

    String value();

    public static class TimestampBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return new Binder<BindTimestamp, Long>() {
                public void bind(SQLStatement q, BindTimestamp bind, Long arg) {
                    q.bind(bind.value(), arg != null ? new Timestamp(arg) : null);
                }
            };
        }
    }
}
