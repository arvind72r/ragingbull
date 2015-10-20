/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.naked.penguin.api;

/**
 * Created by Vamshi Molleti
 */
public class Mentor {
    private String id;
    private String userId;
    private String companyId;
    private Integer upvotes;
    private String positiveComments;
    private String negativeComments;

    public Mentor(String id, String userId, String companyId, Integer upvotes, String positiveComments, String negativeComments) {
        this.id = id;
        this.userId = userId;
        this.companyId = companyId;
        this.upvotes = upvotes;
        this.positiveComments = positiveComments;
        this.negativeComments = negativeComments;
    }
}
