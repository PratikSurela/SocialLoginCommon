package com.app.sociallogin.model;

/**
 * Created by nct119 on 1/6/17.
 */

public class SocialLoginItems {
    public String getStrFirstName() {
        return strFirstName;
    }

    public void setStrFirstName(String strFirstName) {
        this.strFirstName = strFirstName;
    }

    public String getStrLastName() {
        return strLastName;
    }

    public void setStrLastName(String strLastName) {
        this.strLastName = strLastName;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public String getStrFBId() {
        return strFBId;
    }

    public void setStrFBId(String strFBId) {
        this.strFBId = strFBId;
    }

    public String getStrProfilePic() {
        return strProfilePic;
    }

    public void setStrProfilePic(String strProfilePic) {
        this.strProfilePic = strProfilePic;
    }

    private String strFirstName, strLastName, strEmail, strUserName, strGender, strFBId, strProfilePic;

    public SocialLoginItems() {

    }

    public SocialLoginItems(String strFBId, String strFirstName, String strLastName, String strEmail, String strUserName, String strGender, String strProfilePic) {

        this.strFBId = strFBId;
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        this.strEmail = strEmail;
        this.strUserName = strUserName;
        this.strGender = strGender;
        this.strProfilePic = strProfilePic;
    }
}