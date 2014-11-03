package com.sviat.k.androidphones.app.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;

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

        //generateDummyData();
        fetchContactData();
    }

    private void generateDummyData() {
        for (int i = 0; i < 15; i++) {
            ShortContactData cd = new ShortContactData();

            cd.setFirstName("first");
            cd.setLastName("last" + i);
            cd.setPhone(String.format("%d", 12345678 + i));
            cd.setLastContacted(new Date().toString());

            mData.add(cd);
        }
    }

    private void fetchContactData() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        Cursor phones = appContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ShortContactData cd = new ShortContactData();

            cd.setFirstName(name);
            //cd.setLastName("last" + i);
            cd.setPhone(phoneNumber);
            //cd.setLastContacted(new Date().toString());

            mData.add(cd);
        }

        phones.close();
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