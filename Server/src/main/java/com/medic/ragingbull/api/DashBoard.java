/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public class DashBoard {
    private User user;
    private Member member;
    private Practitioner practitioner;
    private Pharmacist pharmacist;

    private class User {
        public List<Object> current = new ArrayList<>();
        public List<Object> pending = new ArrayList<>();
    }

    private class Member {
        public List<Object> current = new ArrayList<>();
        public List<Object> pending = new ArrayList<>();
    }

    private class Practitioner {
        public List<Object> current = new ArrayList<>();
        public List<Object> pending = new ArrayList<>();
    }

    private class Pharmacist {
        public List<Object> current = new ArrayList<>();
        public List<Object> pending = new ArrayList<>();
    }

    public DashBoard() {
        user = new User();
        member = new Member();
        practitioner = new Practitioner();
        pharmacist = new Pharmacist();
    };

    public List<Object> getUserCurrent() {
        return user.current;
    }

    public List<Object> getUserPending() {
        return user.pending;
    }

    public List<Object> getMemberCurrent() {
        return member.current;
    }

    public List<Object> getMemberPending() {
        return member.pending;
    }

    public List<Object> getPractitionerCurrent() {
        return practitioner.current;
    }

    public List<Object> getPractitionerPending() {
        return practitioner.pending;
    }
}
