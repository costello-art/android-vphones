package com.sviat.k.androidphones.app.contacts;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDatabase {
    public static ContactDatabase sContactDatabase;

    private ArrayList<ShortContactData> mData;
    private Context appContext;

    public ContactDatabase(Context appContext) {
        this.appContext = appContext;

        mData = new ArrayList<ShortContactData>();
    }

    public ShortContactData getContact(int id) {
        for (ShortContactData cd : mData) {
            if (cd.getId() == id) {
                return cd;
            }
        }

        return null;
    }

    public static ContactDatabase get(Context c) {
        if (sContactDatabase == null) {
            sContactDatabase = new ContactDatabase(c.getApplicationContext());
        }

        return sContactDatabase;
    }

    public ArrayList<ShortContactData> getContacts() {
        return mData;
    }
}