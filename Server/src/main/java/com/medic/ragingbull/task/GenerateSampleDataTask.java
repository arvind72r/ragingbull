/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.config.MemberTaskConfiguration;
import com.medic.ragingbull.config.RagingBullConfiguration;
import com.medic.ragingbull.config.UserTaskConfiguration;
import com.medic.ragingbull.core.access.roles.UserRoles;
import com.medic.ragingbull.core.constants.LocationSpeciality;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.core.services.*;
import io.dropwizard.servlets.tasks.Task;
import org.joda.time.DateTime;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vamshi Molleti
 */
public class GenerateSampleDataTask extends Task {

    private RagingBullConfiguration configuration;
    private UserService userService;
    private PractitionerService practitionerService;
    private PractitionerLocationService practitionerLocationService;
    private ConsultationService consultationService;
    private PrescriptionService prescriptionService;

    @Inject
    public GenerateSampleDataTask(String name, RagingBullConfiguration configuration, UserService userService, PractitionerService practitionerService, PractitionerLocationService practitionerLocationService, ConsultationService consultationService, PrescriptionService prescriptionService) {
        super(name);
        this.configuration = configuration;
        this.userService = userService;
        this.practitionerService = practitionerService;
        this.practitionerLocationService = practitionerLocationService;
        this.consultationService = consultationService;
        this.prescriptionService = prescriptionService;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, PrintWriter printWriter) throws Exception {
        UserTaskConfiguration userConfig = configuration.getTaskConfiguration().getUserTaskConfiguration();
        MemberTaskConfiguration memberConfig = configuration.getTaskConfiguration().getMemberTaskConfiguration();

        int memberCount = memberConfig.getCount();
        int practitionerCount = configuration.getTaskConfiguration().getPractitionerTaskConfiguration().getCount();
        int practitionerLocationCount = configuration.getTaskConfiguration().getPractitionerLocationTaskConfiguration().getCount();
        int pharmacistCount = configuration.getTaskConfiguration().getPharmacistTaskConfiguration().getCount();
        int pharmacyCount = configuration.getTaskConfiguration().getPharmacyTaskConfiguration().getCount();;

        for (int userCount = 0; userCount < userConfig.getCount(); userCount++) {
            User user = new User(String.format(String.format(userConfig.getPrefix(), userCount)),
                    userConfig.getPassword(),
                    String.format(userConfig.getEmailDomain(), userCount),
                    String.valueOf(userConfig.getPhonePrefix() + new Random().nextInt(99999999)),
                    "SELF",
                    "http://lh3.googleusercontent.com/-jAO2qZBu2Zo/AAAAAAAAAAI/AAAAAAAAAAA/Xpo7Ggl9kHE/s64-c-mo/photo.jpg",
                    SystemConstants.Sex.MALE,
                    new DateTime().minusYears(19));

            // Add user to DB
            Session session = userService.register(user);

            // Create 5 members for each user
            for (int count = 0; count < memberCount; count++) {
                Member member = new Member(
                        String.format(String.format(memberConfig.getPrefix(), String.valueOf(userCount + ""+ count))),
                        String.format(memberConfig.getEmailDomain(), String.valueOf(userCount + ""+ count)),
                        String.valueOf(memberConfig.getPhonePrefix() + new Random().nextInt(99999999)),
                        SystemConstants.Sex.MALE,
                        new DateTime().minusYears(19));
                userService.addMember(session, session.getUserId(), member);
            }

            // Make first n users as practitioners
            if (practitionerCount > 0 ) {
                Practitioner practitioner = new Practitioner("Test Description", user.getPhone(), "9885977975", "BFIPM1982E", "WTFID", "DummyRegistrationID", "Indian Govt", "Dummy License");
                PractitionerResponse response = practitionerService.createPractitioner(session, practitioner);
                // Add location to first n practitioners
                if (practitionerLocationCount > 0 ) {
                    PractitionerLocation practitionerLocation = new PractitionerLocation(String.format("ABC Super Speciality Hospital %d", userCount),
                            String.format("This is test description for the location %d", userCount), LocationSpeciality.CARDIOLOGY‎, "Indiranagar",
                            user.getPhone(), "9886577575", "Test Building Name", "Test Street, Bangalore", "Bangalore", "KA", 560075l, "INDIA", "Test Landmark", 12.9539974f, 77.6309395f, 12 , 12, "DummyLicense");
                    PractitionerLocationResponse locationResponse = practitionerLocationService.createPractitionerLocation(session, response.getId(), practitionerLocation);
                    practitionerLocationCount--;

                    List<UserRoles.Permissions> permissions = new ArrayList<>();
                    permissions.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD);
                    permissions.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY);
                    permissions.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY);
                    permissions.add(UserRoles.Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD);
                    EntityUser entityUser = new EntityUser(session.getUserId(), permissions);
                    // Add current practitioner as entity user
                    practitionerLocationService.addUsers(session, response.getId(), locationResponse.getId(), ImmutableList.of(entityUser));
                }
                practitionerCount--;
            }

            // Make next n users pharmacist
            if (pharmacistCount > 0 ) {
                Pharmacist pharmacist = new Pharmacist();
                // Add n pharmacies
                if (pharmacyCount > 0 ) {
                    PharmacyLocation pharmacyLocation = new PharmacyLocation();
                }
            }
        }
    }
}
