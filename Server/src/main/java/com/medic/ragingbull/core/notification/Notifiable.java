/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.notification;

/**
 * Created by Vamshi Molleti
 */
public abstract class Notifiable {

    public enum Mode {
        MAIL, PUSH, SMS
    }

    public enum NotificationEvent {
        SIGN_UP,
        RESEND_INVITE_CODE,
        NEWSLETTER,
        UPDATES,
        EMERGENCY,
        USER_MEMBER_ADDED,
        USER_MEMBER_REMOVED,
        CONSULTATION_CREATED,
        CONSULTATION_MODIFIED,
        CONSULTATION_DELETED,
        CONSULTATION_SUBMITTED,
        DIAGNOSIS_CREATED,
        DIAGNOSIS_DELETED,
        PRESCRIPTION_CREATED,
        ORDER_CREATED,
        ORDER_ACCEPTED,
        ORDER_DELIVERY_START,
        ORDER_DELIVERY_END,
        ORDER_COMPLETE;
    }
}
