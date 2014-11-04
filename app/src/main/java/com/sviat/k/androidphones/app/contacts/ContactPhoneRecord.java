package com.sviat.k.androidphones.app.contacts;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactPhoneRecord {

    private String mType;
    private String mPhone;

    public ContactPhoneRecord(String type, String phone) {
        this.mType = type;
        this.mPhone = phone;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}