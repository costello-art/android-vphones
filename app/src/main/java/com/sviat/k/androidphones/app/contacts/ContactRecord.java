package com.sviat.k.androidphones.app.contacts;

import java.io.Serializable;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactRecord implements Serializable {
    private String mId;

    private String mFirstName;

    private String mLastName;

    private String mPhone;

    private String mLastContacted;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getPhoneNumber() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getLastCall() {
        return mLastContacted;
    }

    public void setLastContacted(String mLastContacted) {
        this.mLastContacted = mLastContacted;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    /**
     * TODO: refactor
     * @param displayName name to set
     */
    public void setDisplayName(String displayName) {
        mFirstName = displayName;
        mLastName = "";
    }
}