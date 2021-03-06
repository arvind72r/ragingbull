/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.test.resources;

import com.google.common.base.Optional;
import com.medic.ragingbull.api.*;
import com.medic.ragingbull.core.constants.SystemConstants;
import com.medic.ragingbull.test.RagingBullTestApp;
import com.medic.ragingbull.test.generator.*;
import com.medic.ragingbull.test.util.ClientUtil;
import com.medic.ragingbull.test.util.TestConstants;
import com.medic.ragingbull.test.util.TestUtils;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class ConsultationResourceTest extends RagingBullTestApp{

    @Test
    public void testCreateConsultation() {

        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);
        Consultation consultationB = TestConsultation.generateConsultation(consulteeBSession.getUserId(), practitioner.getId(), 2);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response addConsultationResponseB = ClientUtil.postRequestWithAuth(
                consulteeASession.getToken(), TestConstants
                        .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationB, practitionerLocation.getId());
        TestUtils.assertValidResponse(addConsultationResponseB);

    }

    @Test
    public void testGetConsultation() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response getConsultationResponse = ClientUtil.getRequestWithAuth(consulteeASession.getToken(), TestConstants
                .CONSULTATION_GET, Optional.absent(), consultationResponse.getId());
        TestUtils.assertValidResponse(getConsultationResponse);
    }

    @Test
    public void testAddConsultationSymptoms() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response updateSymptomsResponse = ClientUtil.putRequestWithAuth(consulteeASession.getToken(), TestConstants
                .CONSULTATION_UPDATE_NOTES, Optional.absent(), TestConstants.TEST_SYMPTOMS, consultationResponse.getId(), SystemConstants.NotesTypes.SYMPTOMS);
        TestUtils.assertValidResponse(updateSymptomsResponse);

    }

    @Test
    public void testAddConsultationDiagnosis() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response updateSymptomsResponse = ClientUtil.putRequestWithAuth(entityUserASession.getToken(), TestConstants
                .CONSULTATION_UPDATE_NOTES, Optional.absent(), TestConstants.TEST_DIAGNOSIS, consultationResponse.getId(), SystemConstants.NotesTypes.DIAGNOSIS);
        TestUtils.assertValidResponse(updateSymptomsResponse);
    }

    @Test
    public void testAddConsultationDiagnosisForbidden() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response updateSymptomsResponse = ClientUtil.putRequestWithAuth(consulteeASession.getToken(), TestConstants
                .CONSULTATION_UPDATE_NOTES, Optional.absent(), TestConstants.TEST_DIAGNOSIS, consultationResponse.getId(), SystemConstants.NotesTypes.DIAGNOSIS);
        TestUtils.assertForbidden(updateSymptomsResponse);
    }

    @Test
    public void testDeleteConsultation() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response getConsultationResponse = ClientUtil.getRequestWithAuth(consulteeASession.getToken(), TestConstants
                .CONSULTATION_GET, Optional.absent(), consultationResponse.getId());
        TestUtils.assertValidResponse(getConsultationResponse);

        Response deleteConsultationResponse = ClientUtil.deleteRequestWithAuth(entityUserASession.getToken(), TestConstants
                .CONSULTATION_GET, Optional.absent(), consultationResponse.getId());
        TestUtils.assertValidResponse(deleteConsultationResponse);

        Response getDeletedConsultationResponse = ClientUtil.getRequestWithAuth(consulteeASession.getToken(), TestConstants
                .CONSULTATION_GET, Optional.absent(), consultationResponse.getId());
        TestUtils.assertNotFound(getDeletedConsultationResponse);
    }

    @Test
    public void testAddPrescription() {
        User user = TestUser.generateUser(0);
        Response response = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), user);
        Session session = response.readEntity(Session.class);
        TestUtils.validateSession(session);

        User userA = TestUser.generateUser(1);
        Response entityUserAResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userA);
        Session entityUserASession = entityUserAResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserASession);

        User userB = TestUser.generateUser(2);
        Response entityUserBResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userB);
        Session entityUserBSession = entityUserBResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserBSession);

        User userC = TestUser.generateUser(3);
        Response entityUserCResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), userC);
        Session entityUserCSession = entityUserCResponse.readEntity(Session.class);
        TestUtils.validateSession(entityUserCSession);

        User consulteeA = TestUser.generateUser(4);
        Response entityUserDResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeA);
        Session consulteeASession = entityUserDResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeASession);

        User consulteeB = TestUser.generateUser(5);
        Response entityUserEResponse = ClientUtil.postRequest(TestConstants.USER_REGISTRATION_URL, Optional.absent(), consulteeB);
        Session consulteeBSession = entityUserEResponse.readEntity(Session.class);
        TestUtils.validateSession(consulteeBSession);



        Practitioner testPractitioner = TestPractitioner.generatePractitioner(0);
        Response practitionerResponse = ClientUtil.postRequestWithAuth(
                session.getToken(), TestConstants
                        .PRACTITIONER_ADD, Optional.absent(), testPractitioner);
        PractitionerResponse practitioner = practitionerResponse.readEntity(PractitionerResponse.class);
        TestUtils.assertValidResponse(practitionerResponse);

        PractitionerLocation practitionerLocationA = TestPractitionerLocation.createPractitionerLocation(0);
        Response practitionerLocationResponseA = ClientUtil.postRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD, Optional.absent(), practitionerLocationA, practitioner.getId());
        PractitionerLocationResponse practitionerLocation = practitionerLocationResponseA.readEntity(PractitionerLocationResponse.class);
        TestUtils.assertValidResponse(practitionerLocationResponseA);

        EntityUser entityUserA = TestUser.generateLocationEntityUser(entityUserASession.getUserId());
        EntityUser entityUserB = TestUser.generateLocationEntityUser(entityUserBSession.getUserId());
        EntityUser entityUserC = TestUser.generateLocationEntityUser(entityUserCSession.getUserId());

        List<EntityUser> entityUsers = new ArrayList<>();
        entityUsers.add(entityUserA);
        entityUsers.add(entityUserB);
        entityUsers.add(entityUserC);
        // Adding entity users to an entity
        Response addEntityUserAResponse = ClientUtil.putRequestWithAuth(session.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_USER, Optional.absent(), entityUsers, practitionerLocation.getId());
        TestUtils.assertValidResponse(addEntityUserAResponse);

        // Consultation created by front desk
        Consultation consultationA = TestConsultation.generateConsultation(practitioner.getId(), 1);

        Response addConsultationResponseA = ClientUtil.postRequestWithAuth(consulteeASession.getToken(), TestConstants
                .PRACTITIONER_LOCATION_ADD_CONSULTATION, Optional.absent(), consultationA, practitionerLocation.getId());
        ConsultationResponse consultationResponse = addConsultationResponseA.readEntity(ConsultationResponse.class);
        TestUtils.assertValidResponse(addConsultationResponseA);

        Response updateSymptomsResponse = ClientUtil.putRequestWithAuth(entityUserASession.getToken(), TestConstants
                .CONSULTATION_UPDATE_NOTES, Optional.absent(), TestConstants.TEST_DIAGNOSIS, consultationResponse.getId(), SystemConstants.NotesTypes.DIAGNOSIS);
        TestUtils.assertValidResponse(updateSymptomsResponse);

        Prescription prescription = TestPrescription.generatePrescription(consultationResponse.getId(), consulteeASession.getUserId(), true);
        Response addPrescriptionResponse = ClientUtil.postRequestWithAuth(entityUserASession.getToken(), TestConstants
                .PRESCRIPTION_ADD, Optional.absent(), prescription, consultationResponse.getId());
        TestUtils.assertValidResponse(addPrescriptionResponse);

        Response getCurrentPrescription = ClientUtil.getRequestWithAuth(entityUserASession.getToken(), TestConstants
                .CONSULTATION_GET_CURRENT_PRESCRIPTION, Optional.absent(), consultationResponse.getId());
        PrescriptionResponse currentPrescriptionResponse = getCurrentPrescription.readEntity(PrescriptionResponse.class);
        TestUtils.assertValidResponse(getCurrentPrescription);
        TestUtils.assertEquals(currentPrescriptionResponse.getDrugs().size(), prescription.getDrugs().size());
    }
}
