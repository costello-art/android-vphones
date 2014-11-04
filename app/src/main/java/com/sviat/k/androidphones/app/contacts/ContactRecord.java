package com.sviat.k.androidphones.app.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactRecord implements Serializable {
    private String mId;

    private String mFirstName;

    private String mLastName;

    private String mPhone;

    private ArrayList<ContactPhoneRecord> mPhones;

    private String mLastContacted;

    public ContactRecord() {
        mPhones = new ArrayList<ContactPhoneRecord>();
    }

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
        Date date = new Date();
        date.setTime(Long.parseLong(mLastContacted));
        this.mLastContacted = date.toString();
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public ArrayList<ContactPhoneRecord> getPhones() {
        return mPhones;
    }

    /**
     * TODO: refactor
     *
     * @param displayName name to set
     */
    public void setDisplayName(String displayName) {
        mFirstName = displayName;
        mLastName = "";
    }

    public void addPhone(String type, String phone) {
        mPhones.add(new ContactPhoneRecord(type, phone));
    }
}