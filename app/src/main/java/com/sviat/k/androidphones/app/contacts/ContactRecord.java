package com.sviat.k.androidphones.app.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactRecord implements Serializable {
    private String mId;

    private ArrayList<ContactPhoneRecord> mPhones;

    private String mLastContacted;
    private String mDisplayName;

    public ContactRecord() {
        mPhones = new ArrayList<ContactPhoneRecord>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }


    public String getLastCall() {
        return mLastContacted;
    }

    public void setLastContacted(String mLastContacted) {
        Date date = new Date();
        date.setTime(Long.parseLong(mLastContacted));
        this.mLastContacted = date.toString();
    }

    public ArrayList<ContactPhoneRecord> getPhones() {
        return mPhones;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void addPhone(String type, String phone) {
        mPhones.add(new ContactPhoneRecord(type, phone));
    }
}