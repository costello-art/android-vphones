package com.sviat.k.androidphones.app.contacts;

import java.util.ArrayList;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDatabase {
    private ArrayList<ShortContactData> mData;

    public ContactDatabase() {
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
}