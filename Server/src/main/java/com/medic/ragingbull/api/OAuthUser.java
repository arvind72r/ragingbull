/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class OAuthUser {
    private String accessToken;
    private String refreshToken;
    private String value;
    private String displayName;
    private String url;

    public OAuthUser(String accessToken, String refreshToken, String value, String displayName, String url) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.value = value;
        this.displayName = displayName;
        this.url = url;
    }
}
