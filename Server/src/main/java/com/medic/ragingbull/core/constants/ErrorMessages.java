/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.constants;


/**
 * Created by Vamshi Molleti
 */
public interface ErrorMessages {

    String RESOURCE_CREATION_ERROR = "Error creating resource";
    String RESOURCE_NOT_FOUND= "Desired resource not found";

    // Error codes to be used as entities by the platform
    // SignedIn user different from user resource trying to access
    String FORBIDDEN_USER_RESOURCE_CODE = "API401";
    String FORBIDDEN_USER_RESOURCE_MESSAGE = "Can only access logged in user resources";


    String INVALID_PASSWORD_USER_RESOURCE_CODE = "API402";
    String INVALID_PASSWORD_USER_RESOURCE_MESSAGE = "Password mismatch";

    String INVALID_DETAILS_USER_RESOURCE_CODE = "API403";
    String INVALID_DETAILS_USER_RESOURCE_MESSAGE = "Invalid user details";

    String FORBIDDEN_USER_MODIFY_CODE = "API5001";
    String FORBIDDEN_USER_MODIFY_MESSAGE = "User does not have permission to modify";
    String FORBIDDEN_USER_READ_CODE = "API5001";
    String FORBIDDEN_USER_READ_MESSAGE = "User does not have permission to read";

    String FORBIDDEN_ADD_MEMBER_CODE = "API5001";
    String FORBIDDEN_ADD_MEMBER_MESSAGE = "User does not have permission to add member";
    String FORBIDDEN_READ_MEMBER_CODE = "API5002";
    String FORBIDDEN_READ_MEMBER_MESSAGE = "User does not have permission to read member";
}
