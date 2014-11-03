package com.sviat.k.androidphones.app.contacts;

import java.io.Serializable;

/**
 * Created by Sviat on 04.11.14.
 */
public class ShortContactData implements Serializable {
    private int mId;

    private String mFirstName;

    private String mLastName;

    private String mPhone;

    private String mLastContacted;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getLastContacted() {
        return mLastContacted;
    }

    public void setLastContacted(String mLastContacted) {
        this.mLastContacted = mLastContacted;
    }
}