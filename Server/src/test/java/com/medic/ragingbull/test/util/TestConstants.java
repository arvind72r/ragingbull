/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.util;

import com.medic.ragingbull.core.constants.LocationSpeciality;
import com.medic.ragingbull.core.constants.SystemConstants;
import org.joda.time.DateTime;

import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
public interface TestConstants {

    String INPUT_YML = "test-config.yml";

    String HEADER_AUTH = "Auth-Token";
    String HEADER_BASIC_AUTH = "Authorization";


    // Templates

    /*
     _   _
    | | | |
    | | | |___  ___ _ __
    | | | / __|/ _ \ '__|
    | |_| \__ \  __/ |
     \___/|___/\___|_|

    */

    String TEST_USER_EMAIL_TEMPLATE = "user%d@email.com";
    String TEST_USER_NAME_TEMPLATE = "Test%d User";
    String TEST_USER_PICTURE_URL = "testUserPictureUrl";
    String TEST_USER_PASSWORD = "111111";
    //String TEST_USER_PHONE = String.valueOf(new Random(1111111111l).nextInt());
    String TEST_USER_INLET_TYPE = "SELF";
    String TEST_OAUTH_USER_INLET_TYPE = "SELF";
    SystemConstants.Sex TEST_USER_SEX = SystemConstants.Sex.MALE;
    DateTime TEST_USER_DOB_MAJOR = new DateTime().minusYears(19);
    DateTime TEST_USER_DOB_MINOR = new DateTime().minusYears(15);

    /*
    ___  ___               _
    |  \/  |              | |
    | .  . | ___ _ __ ___ | |__   ___ _ __
    | |\/| |/ _ \ '_ ` _ \| '_ \ / _ \ '__|
    | |  | |  __/ | | | | | |_) |  __/ |
    \_|  |_/\___|_| |_| |_|_.__/ \___|_|

     */
    String TEST_MEMBER_NAME_TEMPLATE = "Test%d Member";
    String TEST_MEMBER_EMAIL_TEMPLATE = "member%d@email.com";
    SystemConstants.Sex TEST_MEMBER_SEX = SystemConstants.Sex.MALE;
    DateTime TEST_MEMBER_DOB_MAJOR = new DateTime().minusYears(19);

    /*
    ______               _   _ _   _
    | ___ \             | | (_) | (_)
    | |_/ / __ __ _  ___| |_ _| |_ _  ___  _ __   ___ _ __
    |  __/ '__/ _` |/ __| __| | __| |/ _ \| '_ \ / _ \ '__|
    | |  | | | (_| | (__| |_| | |_| | (_) | | | |  __/ |
    \_|  |_|  \__,_|\___|\__|_|\__|_|\___/|_| |_|\___|_|

     */
    String TEST_PRACTITIONER_DESCRIPTION = "Test Practitioner Description %d";
    Integer TEST_PRACTITIONER_PHONE = 33 + new Random().nextInt(99999999);
    String TEST_PRACTITIONER_PRIMARYID = "Test Practitioner PrimaryId %d";
    String TEST_PRACTITIONER_SECONDARYID = "Test Practitioner SecondaryId %d";
    String TEST_PRACTITIONER_REGISTRATIONID = "Test Practitioner RegistrationId %d";
    String TEST_PRACTITIONER_ISSUING_AUTHORITY = "Test Practitioner Issuing Authority %d";
    String TEST_PRACTITIONER_LICENSE = "Test Practitioner License %d";

    /*
    ______               _   _ _   _                         _                     _   _
    | ___ \             | | (_) | (_)                       | |                   | | (_)
    | |_/ / __ __ _  ___| |_ _| |_ _  ___  _ __   ___ _ __  | |     ___   ___ __ _| |_ _  ___  _ __
    |  __/ '__/ _` |/ __| __| | __| |/ _ \| '_ \ / _ \ '__| | |    / _ \ / __/ _` | __| |/ _ \| '_ \
    | |  | | | (_| | (__| |_| | |_| | (_) | | | |  __/ |    | |___| (_) | (_| (_| | |_| | (_) | | | |
    \_|  |_|  \__,_|\___|\__|_|\__|_|\___/|_| |_|\___|_|    \_____/\___/ \___\__,_|\__|_|\___/|_| |_|

     */

    String TEST_PRACTITIONER_LOCATION_NAME = "Test Practitioner Location Name%d";
    String TEST_PRACTITIONER_LOCATION_DESCRIPTION = "Test Practitioner Description%d";
    LocationSpeciality TEST_PRACTITIONER_LOCATION_SPECIALITY = LocationSpeciality.CARDIOLOGY‎;
    String TEST_PRACTITIONER_LOCATION_LOCATION = "Test Location%d";
    String TEST_PRACTITIONER_LOCATION_PRIMARY_CONTACT = 44  + "" + new Random().nextInt(99999999);
    String TEST_PRACTITIONER_LOCATION_SECONDARY_CONTACT = 44  + "" + new Random().nextInt(99999999);
    String TEST_PRACTITIONER_LOCATION_ADDRESS1 = "Test Practitioner Primary Address %d";
    String TEST_PRACTITIONER_LOCATION_ADDRESS2 = "Test Practitioner Secondary Address %d";
    String TEST_PRACTITIONER_LOCATION_CITY = "Gotham";
    String TEST_PRACTITIONER_LOCATION_STATE = "KA";
    Long TEST_PRACTITIONER_LOCATION_ZIP = 560075L;
    String TEST_PRACTITIONER_LOCATION_COUNTRY = "India";
    Float TEST_PRACTITIONER_LOCATION_LONGITUDE = 12.9539974F;
    Float TEST_PRACTITIONER_LOCATION_LATITUDE = 77.6309395F;
    Integer TEST_PRACTITIONER_LOCATION_WORKING_HOURS = 12;
    Integer TEST_PRACTITIONER_LOCATION_WORKING_DAYS = 12;
    String TEST_PRACTITIONER_LOCATION_LICENSE = "Test License %d";
    String TEST_PRACTITIONER_LOCATION_LANDMARK = "Test Landmark %d";

    /*
     _____                       _ _        _   _
    /  __ \                     | | |      | | (_)
    | /  \/ ___  _ __  ___ _   _| | |_ __ _| |_ _  ___  _ __
    | |    / _ \| '_ \/ __| | | | | __/ _` | __| |/ _ \| '_ \
    | \__/\ (_) | | | \__ \ |_| | | || (_| | |_| | (_) | | | |
     \____/\___/|_| |_|___/\__,_|_|\__\__,_|\__|_|\___/|_| |_|

     */

    String TEST_SYMPTOMS = "Test Symptom";
    String SYMPTOMS = "Test Symptoms %d. a) Test Symptoms A b) Test Symptoms B c) Test Symptoms C";
    String TEST_DIAGNOSIS = "Test Diagnosis";
    // URLS
    String BASE_URL = "http://localhost:8080";

    String USER_REGISTRATION_URL = BASE_URL + "/register";
    String OAUTH_USER_REGISTRATION_URL = BASE_URL + "/register/oauth";
    String USER_RESEND_AUTH_CODE = BASE_URL + "/register/%s";
    String USER_AUTH_CODE_APPROVE = BASE_URL + "/register/%s/approve";

    String USER_LOGIN = BASE_URL + "/auth/login";
    String USER_LOGOUT = BASE_URL + "/auth/logout";
    String USER_RESET = BASE_URL + "/auth/reset";
    String USER_RESET_PASSWORD = BASE_URL + "/auth/reset/%s";

    String USER_GET_DETAILS = BASE_URL + "/user/%s";
    String USER_ADD_MEMBER = BASE_URL + "/user/%s/member";
    String USER_UPDATE_DETAILS = BASE_URL + "/user/%s/modify/%s";

    String PRACTITIONER_ADD = BASE_URL + "/practitioner";
    String PRACTITIONER_GET = BASE_URL + "/practitioner/%s";

    String PRACTITIONER_LOCATION_ADD = BASE_URL + "/practitioner/%s/location";

    String PRACTITIONER_LOCATION_GET = BASE_URL + "/location/%s";
    String PRACTITIONER_LOCATION_ADD_USER = BASE_URL + "/location/%s/users";
    String PRACTITIONER_LOCATION_GET_USERS = BASE_URL + "/location/%s/users";

    String PRACTITIONER_LOCATION_ADD_CONSULTATION = BASE_URL + "/location/%s/consultation";
    String CONSULTATION_UPDATE_NOTES = BASE_URL + "/consultation/%s/notes/%s";
    String CONSULTATION_GET = BASE_URL + "/consultation/%s";
    String PRESCRIPTION_ADD = BASE_URL + "/consultation/%s/prescription";
    String CONSULTATION_GET_CURRENT_PRESCRIPTION = BASE_URL + "/consultation/%s/prescription";

    /*
    ______                        _       _   _
    | ___ \                      (_)     | | (_)
    | |_/ / __ ___  ___  ___ _ __ _ _ __ | |_ _  ___  _ __
    |  __/ '__/ _ \/ __|/ __| '__| | '_ \| __| |/ _ \| '_ \
    | |  | | |  __/\__ \ (__| |  | | |_) | |_| | (_) | | | |
    \_|  |_|  \___||___/\___|_|  |_| .__/ \__|_|\___/|_| |_|
                                   | |
                                   |_|
    */

    String NAME = "Test Drug %d";
    Integer FREQUENCY = 1;
    Integer DOSE = 1;
    String UNIT = "Test Allergy %d";
    Integer DAYS = 1;
}
