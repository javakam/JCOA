package com.vitek.jcoa.net.entity;

/**
 * Created by javakam on 2017/5/18 0018.
 */

public class BaseEntity {

    private boolean ispass;
    private String defaultMessage;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "ispass=" + ispass +
                ", defaultMessage='" + defaultMessage + '\'' +
                '}';
    }

    public boolean ispass() {
        return ispass;
    }

    public void setIspass(boolean ispass) {
        this.ispass = ispass;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
