/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

/**
 * Created by Vamshi Molleti
 */
public class Athlete {
    private String id;
    private String userId;
    private String trainingPartnerId;
    private String level;

    public Athlete(String id, String userId, String trainingPartnerId, String level) {
        this.id = id;
        this.userId = userId;
        this.trainingPartnerId = trainingPartnerId;
        this.level = level;
    }
}
