/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.exception;

/**
 * Created by Vamshi Molleti
 */
public class RagingBullBaseException extends Exception{
    public RagingBullBaseException() {
    }

    public RagingBullBaseException(String message) {
        super(message);
    }

    public RagingBullBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RagingBullBaseException(Throwable cause) {
        super(cause);
    }

    public RagingBullBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
