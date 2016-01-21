/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.api;

/**
 * Created by Vamshi Molleti
 */
public class Address {
    private String id;
    private String userId;
    private String label;
    private String address1;
    private String address2;
    private Integer zip;
    private Float longitude;
    private Float latitude;

    public Address() {}

    public Address(String id, String userId, String label, String address1, String address2, Integer zip, Float longitude, Float latitude) {
        this.id = id;
        this.userId = userId;
        this.label = label;
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLabel() {
        return label;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Integer getZip() {
        return zip;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }
}
