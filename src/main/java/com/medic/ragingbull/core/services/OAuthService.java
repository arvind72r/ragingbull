/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.medic.ragingbull.api.Session;
import com.medic.ragingbull.api.User;
import com.medic.ragingbull.config.FacebookConfiguration;
import com.medic.ragingbull.config.GoogleConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.core.access.RoleGenerator;
import com.medic.ragingbull.core.access.roles.Role;
import com.medic.ragingbull.core.constants.Ids;
import com.medic.ragingbull.core.constants.InletType;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.exception.ResourceCreationException;
import com.medic.ragingbull.jdbi.dao.OAuthDao;
import com.medic.ragingbull.jdbi.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Vamshi Molleti
 */
public class OAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthService.class);
    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private GoogleConfiguration googleConfiguration;
    private FacebookConfiguration facebookConfiguration;
    private UserService userService;
    private OAuthDao oAuthDao;
    private UserDao userDao;
    private Client httpClient;

    public enum Providers {
        GOOGLE("gg"), FACEBOOK("fb");
        private String providerName;

        Providers(String providerName) {
            this.providerName = providerName;
        }

        public String getProviderName() {
            return providerName;
        }
    }

    @Inject
    public OAuthService(RagingBullConfiguration ragingBullConfiguration, OAuthDao oAuthDao, UserDao userDao, UserService userService, Client httpClient) {
        this.googleConfiguration = ragingBullConfiguration.getGoogleConfiguration();
        this.facebookConfiguration = ragingBullConfiguration.getFacebookConfiguration();
        this.oAuthDao = oAuthDao;
        this.userDao = userDao;
        this.userService = userService;
        this.httpClient = httpClient;
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

    private String generateFaceBookAuthorizationUrl() {
        String authUrl = "https://www.facebook.com/dialog/oauth?client_id=" + facebookConfiguration.getClientId() + "&scope=public_profile,email&redirect_uri=" + facebookConfiguration.getRedirectUrl();
        return authUrl;
    }

    public Session getProfileInfoFromOAuth(Providers provider, final @Nonnull String authCode)
            throws Exception {

        if (provider == Providers.GOOGLE) {
            final Credential googleCredential = retrieveGoogleToken(authCode);

            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, googleCredential)
                    .setApplicationName(googleConfiguration.getApplicationName()).build();
            final Person profile = plus.people().get("me").execute();

            // Check if user exists in our system. If no, create him.
            User user = userDao.getByEmail(profile.getEmails().get(0).getValue());

            if (user == null) {
                // User not present in our system
                String userId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER);
                String email = StringUtils.lowerCase(profile.getEmails().get(0).getValue());

                int userCreated = userDao.createUser(userId, profile.getDisplayName(), email, null, null, InletType.GOOGLE.getInletType(), RoleGenerator.generateRole(Role.NATIVE_USER), profile.getImage().getUrl());
                if (userCreated == 0) {
                    LOGGER.error(String.format("Error registering oauth user %s.", profile.getEmails().get(0).getValue()));
                    throw new ResourceCreationException("Error registering user. Please try again");
                }

                user = new User(profile.getDisplayName(), null, profile.getEmails().get(0).getValue(), null, InletType.GOOGLE.getInletType(), profile.getImage().getUrl(), null, null);
                user.setId(userId);
            }

            // Save refresh token to table
            String oauthId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.OAUTH);
            int oauthCreated = oAuthDao.create(oauthId, user.getId(), googleCredential.getAccessToken(), googleCredential.getRefreshToken(), Providers.GOOGLE.getProviderName());
            if (oauthCreated == 0) {
                LOGGER.error(String.format("Error creating oauth entry %s.", profile.getEmails().get(0).getValue()));
                throw new ResourceCreationException("Error registering user. Please try again");
            }

            //TODO:  Check if the picture URL is same that is stored in DB

            // Generate session
            Session session = userService.getSession(user);
            return session;
        } else {

            String accessToken = retrieveFacebookToken(authCode);

            StringBuilder tokenUrlBuilder = new StringBuilder();
            tokenUrlBuilder.append(SystemConstants.FACEBOOK_GRAPH_API_URL + "me");
            WebTarget target = httpClient.target(tokenUrlBuilder.toString());
            target = target.queryParam("access_token", accessToken);
            target = target.queryParam("fields", "id,name,email,cover");
            Response response = target.request().buildGet().invoke();
            Map<String, Object> profileMap = response.readEntity(Map.class);
            Map<String, Object> cover = (Map<String, Object>) profileMap.get("cover");

            String name = (String) profileMap.get("name");
            String facebookEmail = (String) profileMap.get("email");
            String pictureUrl = (String) cover.get("source");

            User user = userDao.getByEmail(facebookEmail);

            if (user == null) {
                // User not present in our system
                String userId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.USER);
                String email = StringUtils.lowerCase(facebookEmail);


                int userCreated = userDao.createUser(userId, name, email, null, null, InletType.FACEBOOK.getInletType(), RoleGenerator.generateRole(Role.NATIVE_USER), pictureUrl);
                if (userCreated == 0) {
                    LOGGER.error(String.format("Error registering oauth user %s.", facebookEmail));
                    throw new ResourceCreationException("Error registering user. Please try again");
                }
                user = new User(name, null, email, null, InletType.FACEBOOK.getInletType(), pictureUrl, null, null);
                user.setId(userId);
            }

            // Save refresh token to table
            String oauthId = com.medic.ragingbull.util.Ids.generateId(Ids.Type.OAUTH);
            int oauthCreated = oAuthDao.create(oauthId, user.getId(), accessToken, accessToken, Providers.FACEBOOK.getProviderName());
            if (oauthCreated == 0) {
                LOGGER.error(String.format("Error creating oauth entry %s.", facebookEmail));
                throw new ResourceCreationException("Error registering user. Please try again");
            }

            //TODO:  Check if the picture URL is same that is stored in DB

            // Generate session
            Session session = userService.getSession(user);
            return session;
        }
    }

    private String retrieveFacebookToken(final @Nonnull String authCode) throws IOException {
        StringBuilder tokenUrlBuilder = new StringBuilder();
        tokenUrlBuilder.append("https://graph.facebook.com/v2.3/oauth/access_token?client_id=" + facebookConfiguration.getClientId() + "&redirect_uri=" + facebookConfiguration.getRedirectUrl() + "&client_secret=" + facebookConfiguration.getSecret() + "&code=" + authCode);
        WebTarget target = httpClient.target(tokenUrlBuilder.toString());
        Response response = target.request().buildGet().invoke();
        String authResponse = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> authResponseMap;
        authResponseMap = mapper.readValue(authResponse, Map.class);
        String authToken = (String) authResponseMap.get("access_token");
        return authToken;
    }


    private Credential retrieveGoogleToken(final @Nonnull String code) throws IOException {
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
        }
        catch (IOException io) {

        }
        catch (TokenResponseException e) {
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


}
