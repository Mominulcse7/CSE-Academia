package com.cseacademia.mominulcse1213.cseacademia;

/**
 * Created by Ripon on 10/7/2018.
 */

class UserRegistrationConstractor {
    private String id;
    private String userName;
    private String userRoll;
    private String userSession;
    private String userPhone;
    private String userEmail;
    private String macAddress;

    public UserRegistrationConstractor()
    {

    }

    public UserRegistrationConstractor(String id, String userName, String userRoll, String userSession, String userPhone, String userEmail, String macAddress) {
        this.id = id;
        this.userName = userName;
        this.userRoll = userRoll;
        this.userSession = userSession;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.macAddress = macAddress;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRoll() {
        return userRoll;
    }

    public String getUserSession() {
        return userSession;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getMacAddress() {
        return macAddress;
    }
}
