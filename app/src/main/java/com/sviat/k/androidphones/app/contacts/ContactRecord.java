package com.sviat.k.androidphones.app.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactRecord implements Serializable {
    private static final String TAG = "ContactRecord";
    private String mId;

    private ArrayList<ContactPhoneRecord> mPhones;
    private ArrayList<ContactEmailRecord> mEmails;

    private String mLastContacted;
    private String mDisplayName;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getLastCall() {
        if (mLastContacted == null) {
            return "no data";
        }

        return mLastContacted;
    }

    public void setLastContacted(String mLastContacted) {
        Date date = new Date();
        date.setTime(Long.parseLong(mLastContacted));
        this.mLastContacted = date.toString();
    }

    public void addPhone(String type, String phone) {
        if (mPhones == null) {
            mPhones = new ArrayList<ContactPhoneRecord>();
        }

        mPhones.add(new ContactPhoneRecord(type, phone));
    }

    public ArrayList<ContactPhoneRecord> getPhones() {
        return mPhones;
    }

    public void addEmail(String type, String email) {
        if (mEmails == null) {
            mEmails = new ArrayList<ContactEmailRecord>();
        }

        mEmails.add(new ContactEmailRecord(type, email));
    }

    public ArrayList<ContactEmailRecord> getEmails() {
        return mEmails;
    }
}