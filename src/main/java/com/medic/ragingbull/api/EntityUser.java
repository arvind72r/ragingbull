/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medic.ragingbull.core.access.UserRoles;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.constants.ValidationConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityUser {

    @JsonIgnore
    private String id;

    @NotEmpty(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    @JsonProperty
    private String userId;

    @NotNull(message = ValidationConstants.MANDATORY_PARAM_MISSING)
    private List<UserRoles.Permissions> permissions;

    @JsonIgnore
    private String creatorId;

    @JsonIgnore
    private String entityId;

    @JsonProperty
    private SystemConstants.Entities entity;

    @JsonProperty
    private DateTime createdAt;

    @JsonProperty
    private DateTime updatedAt;


    public EntityUser() {}

    public EntityUser(String userId, List<UserRoles.Permissions> permissions) {
        this.userId = userId;
        this.permissions = permissions;
    }

    public EntityUser(String id, String userId, String creatorId, String entityId, SystemConstants.Entities entity, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.creatorId = creatorId;
        this.entityId = entityId;
        this.entity = entity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public EntityUser(String id, String userId, List<UserRoles.Permissions> permissions, String creatorId, String entityId, SystemConstants.Entities entity, DateTime createdAt, DateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.permissions = permissions;
        this.creatorId = creatorId;
        this.entityId = entityId;
        this.entity = entity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setEntity(SystemConstants.Entities entity) {
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public List<UserRoles.Permissions> getPermissions() {
        return permissions;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getEntityId() {
        return entityId;
    }

    public SystemConstants.Entities getEntity() {
        return entity;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}
