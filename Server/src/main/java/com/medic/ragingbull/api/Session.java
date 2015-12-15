/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medic.ragingbull.core.access.roles.UserRoleGenerator;
import com.medic.ragingbull.core.access.roles.UserRoles;
import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Vamshi Molleti
 */
public class Session {

    private String token;
    private String userEmail;
    private String userId;
    private String phone;
    private Long role;
    private Boolean isUserValid;
    private DateTime createdAt;
    private DateTime expiry;

    @JsonIgnore
    private UserRoles.Role userRole;

    public Session() {}

    public Session(String token, String userId, String userEmail, Long role, DateTime createdAt, DateTime expiry) {
        this.token = token;
        this.userEmail = userEmail;
        this.userId = userId;
        this.role = role;
        this.userRole = UserRoleGenerator.generateRole(this.role);
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

    public Session(String token, String userEmail, String userId, String phone, Long role, Boolean isUserValid, DateTime createdAt, DateTime expiry) {
        this.token = token;
        this.userEmail = userEmail;
        this.userId = userId;
        this.phone = phone;
        this.role = role;
        this.userRole = UserRoleGenerator.generateRole(this.role);
        this.isUserValid = isUserValid;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }

    public void setIsUserValid(Boolean isUserValid) {
        this.isUserValid = isUserValid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRole() {
        return role;
    }

    public Boolean getIsUserValid() {
        return isUserValid;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public UserRoles.Role getUserRole() {
        if (userRole == null) {
            for (UserRoles.Role role : UserRoles.Role.values()) {

            }
        }
        return userRole;
    }
}
