/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class DashBoard {
    @JsonProperty
    private User user;

    @JsonProperty
    private Member member;

    @JsonProperty
    private Practitioner practitioner;
    private Pharmacist pharmacist;

    private class User {
        public List<Consultation> current = new ArrayList<>();
        public List<Consultation> past = new ArrayList<>();
    }

    private class Member {
        public List<Consultation> current = new ArrayList<>();
        public List<Consultation> past = new ArrayList<>();
    }

    private class Practitioner {
        public List<Consultation> current = new ArrayList<>();
        public List<Consultation> past = new ArrayList<>();
    }

    private class Pharmacist {
        public List<Object> current = new ArrayList<>();
        public List<Object> past = new ArrayList<>();
    }

    public DashBoard() {
        user = new User();
        member = new Member();
        practitioner = new Practitioner();
        pharmacist = new Pharmacist();
    };

    public List<Consultation> getUserCurrent() {
        return user.current;
    }

    public List<Consultation> getUserPast() {
        return user.past;
    }

    public List<Consultation> getMemberCurrent() {
        return member.current;
    }

    public List<Consultation> getMemberPast() {
        return member.past;
    }

    public List<Consultation> getPractitionerCurrent() {
        return practitioner.current;
    }

    public List<Consultation> getPractitionerPast() {
        return practitioner.past;
    }
}
