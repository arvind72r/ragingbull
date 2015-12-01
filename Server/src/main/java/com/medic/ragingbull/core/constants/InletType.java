package com.medic.ragingbull.core.constants;

/**
 * Created by Vamshi Molleti
 */
public enum InletType {

    SELF("self"), GOOGLE("google"), FACEBOOK("facebook"), ANON ("anon");

    private String inletType;

    InletType(String inletType) {
        this.inletType = inletType;
    }

    public String getInletType() {
        return inletType;
    }



}
