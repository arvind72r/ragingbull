/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.mapper;

import com.medic.ragingbull.api.Drug;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Created by Vamshi Molleti
 */
@BindingAnnotation(BindDrug.EnumBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindDrug {
    public static class EnumBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return new Binder<BindDrug, Drug>() {
                public void bind(SQLStatement q, BindDrug bind, Drug arg) {
                    q.bind("id", arg.getId());
                    q.bind("consultationId", arg.getConsultationId());
                    q.bind("practitionerId", arg.getPractitionerId());
                    q.bind("prescriptionId", arg.getPrescriptionId());
                    q.bind("userId", arg.getUserId());
                    q.bind("name", arg.getName());
                    q.bind("frequency", arg.getFrequency());
                    q.bind("schedule", arg.getSchedule().getBitValue());
                    q.bind("dose", arg.getFrequency());
                    q.bind("unit", arg.getUnit());
                    q.bind("days", arg.getDays());
                }
            };
        }
    }
}
