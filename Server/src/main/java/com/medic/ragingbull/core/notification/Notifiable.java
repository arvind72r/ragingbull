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

    public enum Mode{
        MAIL,PUSH,SMS
    }

    public enum Event
    {
        SIGN_UP, SIGN_UP_SUCCESS
    }
}
