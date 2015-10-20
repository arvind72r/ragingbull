/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

import org.joda.time.DateTime;

/**
 * Created by Vamshi Molleti
 */
public class LoginResponse {

    private String sessionId;
    private User user;
    private DateTime expriry;

    public LoginResponse(String sessionId, DateTime expriry, User user) {
        this.sessionId = sessionId;
        this.user = user;
        this.expriry = expriry;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DateTime getExpriry() {
        return expriry;
    }

    public void setExpriry(DateTime expriry) {
        this.expriry = expriry;
    }
}
