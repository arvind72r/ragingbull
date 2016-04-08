/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.CartItem;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Created by Vamshi Molleti
 */
@BindingAnnotation(BindCartItem.EnumBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindCartItem {
    public static class EnumBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return new Binder<BindCartItem, CartItem>() {
                public void bind(SQLStatement q, BindCartItem bind, CartItem arg) {
                    q.bind("id", arg.getId());

                }
            };
        }
    }
}
