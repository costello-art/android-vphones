package com.sviat.k.androidphones.app.contacts;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactEmailRecord {
    private String mEmail;
    private String mType;

    public ContactEmailRecord(String type, String email) {
        this.mEmail = email;
        this.mType = type;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
