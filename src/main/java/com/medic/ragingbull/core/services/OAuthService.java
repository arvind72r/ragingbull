/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.inject.Inject;
import com.medic.ragingbull.api.OAuthUser;
import com.medic.ragingbull.config.GoogleConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by Vamshi Molleti
 */
public class OAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthService.class);
    private GoogleConfiguration googleConfiguration;
    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public enum Providers {
        GOOGLE("gg"), FACEBOOK("fb");
        String providerName;

        Providers(String providerName) {
            this.providerName = providerName;
        }

        public String getProviderName() {
            return providerName;
        }
    }

    @Inject
    public OAuthService(RagingBullConfiguration ragingBullConfiguration) {
        this.googleConfiguration = ragingBullConfiguration.getGoogleConfiguration();
    }


    public String getAuthorizationUrl(Providers provider) {
        if (provider == Providers.GOOGLE) {
            return generateGoogleAuthorizationUrl();
        } else {
            return generateFaceBookAuthorizationUrl();
        }
    }

    private String generateGoogleAuthorizationUrl() {

        String scope = "https://www.googleapis.com/auth/plus.profiles.read https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.profile.emails.read";
        String authUrl = "https://accounts.google.com/o/oauth2/auth?" +
                "scope=" + scope +
                "&redirect_uri=" + googleConfiguration.getRedirectUrl() +
                "&response_type=code" +
                "&access_type=offline" +
                "&approval_prompt=force" +
                "&client_id=" + googleConfiguration.getClientId();

        return authUrl;
    }

    public OAuthUser getProfileInfoFromOAuth(Providers provider, final @Nonnull String authCode)
            throws Exception {

        if (provider == Providers.GOOGLE) {
            final Credential googleCredential = retrieveToken(authCode);

            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, googleCredential)
                    .setApplicationName(googleConfiguration.getApplicationName()).build();
            final Person profile = plus.people().get("me").execute();

            return new OAuthUser(googleCredential.getAccessToken(),
                    googleCredential.getRefreshToken(),
                    profile.getEmails().get(0).getValue(),
                    profile.getDisplayName(),
                    profile.getImage().getUrl());
        }
        return null;
    }


    private Credential retrieveToken(final @Nonnull String code) throws IOException {
        Credential credential = null;
        try {

            final GoogleAuthorizationCodeTokenRequest req = new GoogleAuthorizationCodeTokenRequest(
                    TRANSPORT,
                    JSON_FACTORY,
                    googleConfiguration.getClientId(),
                    googleConfiguration.getSecret(),
                    code,
                    googleConfiguration.getRedirectUrl());


            final GoogleTokenResponse response = req.execute();
            response.parseIdToken().getPayload().getEmail();
            credential = new GoogleCredential.Builder()
                    .setClientSecrets(
                            googleConfiguration.getClientId(),
                            googleConfiguration.getSecret())
                    .setTransport(TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .build();
            credential.setAccessToken(response.getAccessToken());
            credential.setRefreshToken(response.getRefreshToken());
        }  catch (TokenResponseException e) {
            if (e.getDetails() != null) {
                LOGGER.error("Error retrieving google auth token: " + e.getDetails());
                if (e.getDetails().getErrorDescription() != null) {
                    LOGGER.error("Google auth error description: " + e.getDetails().getErrorDescription());
                }
                if (e.getDetails().getErrorUri() != null) {
                    LOGGER.error("Google auth error uri: " + e.getDetails().getErrorUri());
                }
                throw new RuntimeException("An unknown problem occurred while retrieving oauth token");
            }
        }
        return credential;
    }

    private String generateFaceBookAuthorizationUrl() {

        return null;
    }




}
