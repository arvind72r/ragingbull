/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.config;

/**
 * Created by Vamshi Molleti
 */
public class ValidationConstants {
    public static final int EMAIL_MIN = 3;
    public static final int EMAIL_MAX = 320;
    public static final String EMAIL_MSG_SIZE = "Email must be between "+EMAIL_MIN+" and "+EMAIL_MAX+" characters";
    public static final String EMAIL_MSG_EMPTY = "The email address cannot be empty";
    public static final String EMAIL_MSG_INVALID = "Not a valid email address";

    public static final int PASSWORD_MIN = 3;
    public static final int PASSWORD_MAX = 128;
    public static final String PASSWORD_MSG_SIZE = "Password must be between "+PASSWORD_MIN+" and "+PASSWORD_MAX+" characters";
    public static final String PASSWORD_MSG_EMPTY = "The password cannot be empty";

    public static final int NAME_MIN = 3;
    public static final int NAME_MAX = 128;
    public static final String NAME_MSG_SIZE = "Name must be between "+ NAME_MIN +" and "+ NAME_MAX +" characters";
    public static final String NAME_MSG_EMPTY = "The name cannot be empty";

    public static final int CONTACT_NAME_MIN = 3;
    public static final int CONTACT_NAME_MAX = 128;
    public static final String CONTACT_NAME_MSG_SIZE = "Contact name must be between "+ CONTACT_NAME_MIN +" and "+ CONTACT_NAME_MAX +" characters";
    public static final String CONTACT_NAME_MSG_EMPTY = "The contact name cannot be empty";

    public static final int LOCATION_NAME_MIN = 3;
    public static final int LOCATION_NAME_MAX = 128;
    public static final String LOCATION_NAME_MSG_SIZE = "Location must be between "+ CONTACT_NAME_MIN +" and "+ CONTACT_NAME_MAX +" characters";
    public static final String LOCATION_NAME_MSG_EMPTY = "The location cannot be empty";

    public static final int PHONE_MIN = 1;
    public static final int PHONE_MAX = 15;
    public static final String PHONE_MSG_SIZE = "Mobile must be between "+ PHONE_MIN +" and "+ PHONE_MAX +" characters";
    public static final String PHONE_MSG_EMPTY = "The mobile number cannot be empty";

    public static final int PIN_MIN= 3;
    public static final int PIN_MAX = 16;
    public static final String PIN_MSG_SIZE = "PIN must be between "+PIN_MIN+" and "+PIN_MAX+" characters";

    public static final int ADDRESS1_MIN= 1;
    public static final int ADDRESS1_MAX = 100;
    public static final String ADDRESS1_MSG_SIZE = "Address 1 must be between "+ADDRESS1_MIN+" and "+ADDRESS1_MAX+" characters";

    public static final int ADDRESS2_MIN= 0;
    public static final int ADDRESS2_MAX = 100;
    public static final String ADDRESS2_MSG_SIZE = "Address 2 must be between "+ADDRESS2_MIN+" and "+ADDRESS2_MAX+" characters";

    public static final int CITY_MIN= 0;
    public static final int CITY_MAX = 40;
    public static final String CITY_MSG_SIZE = "City must be between "+CITY_MIN+" and "+CITY_MAX+" characters";
    public static final String CITY_MSG_EMPTY = "The city name cannot be empty";

    public static final int STATE_MIN= 0;
    public static final int STATE_MAX = 2;
    public static final String STATE_MSG_SIZE = "State must be between "+STATE_MIN+" and "+STATE_MAX+" characters";
    public static final String STATE_MSG_EMPTY = "The state name cannot be empty";

    public static final int ZIP_MIN= 6;
    public static final int ZIP_MAX = 6;
    public static final String ZIP_MSG_SIZE = "Zip must be between "+ZIP_MIN+" and "+ZIP_MAX+" characters";
    public static final String ZIP_MSG_EMPTY = "Zip code cannot be empty";

    public static final int COUNTRY_MIN= 0;
    public static final int COUNTRY_MAX = 10;
    public static final String COUNTRY_MSG_SIZE = "Country must be between "+COUNTRY_MIN+" and "+COUNTRY_MAX+" characters";
    public static final String COUNTRY_MSG_EMPTY = "Country name cannot be empty";

    public static final int LANDMARK_MIN= 0;
    public static final int LANDMARK_MAX = 128;
    public static final String LANDMARK_MSG_SIZE = "Landmark must be between "+LANDMARK_MIN+" and "+LANDMARK_MAX+" characters";
    public static final String LANDMARK_MSG_EMPTY = "Landmark cannot be empty";

    public static final String DELIVERY_RADIUS_MSG_EMPTY = "Landmark cannot be empty";

    public static final String WORKING_HOURS_MSG_EMPTY = "Landmark cannot be empty";

    public static final String WORKING_DAYS_MSG_EMPTY = "Landmark cannot be empty";
}


