package com.sviat.k.androidphones.app.contacts;

import android.util.Log;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactRecord implements Serializable {
    private static final String TAG = "ContactRecord";
    private String mId;

    private ArrayList<ContactPhoneRecord> mPhones;

    private String mLastContacted;
    private String mDisplayName;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
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
        if (mPhones == null) {
            Log.d(TAG, String.format("Contact \'%s\' has no phones list'", getId()));
            mPhones = new ArrayList<ContactPhoneRecord>();
        }

        mPhones.add(new ContactPhoneRecord(type, phone));
    }
}