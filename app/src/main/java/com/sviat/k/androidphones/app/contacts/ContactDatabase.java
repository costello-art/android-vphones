package com.sviat.k.androidphones.app.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDatabase {
    private static final String TAG = "ContactDatabase";
    public static ContactDatabase sContactDatabase;

    private final Uri uriCommonContactInfo = ContactsContract.Contacts.CONTENT_URI;
    private final Uri uriPhoneContactInfo = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private final Uri uriEmailContactInfo = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

    private ArrayList<ContactRecord> mData;
    private Context appContext;
    private ContentResolver mContactResolver;

    private long timeStart;
    private long timeEnd;

    public ContactDatabase(Context appContext) {
        this.appContext = appContext;

        mData = new ArrayList<ContactRecord>();
        mContactResolver = appContext.getContentResolver();

        fetchContactDataAll();
    }

    private void recStartTime() {
        timeStart = System.currentTimeMillis();
    }

    private void showOperationTime(String title) {
        timeEnd = System.currentTimeMillis();

        long time = (timeEnd - timeStart);
        Log.d(TAG, String.format("Operation \'%s\' took %d ms", title, time));
    }

    private void fetchContactDataAll() {
        Cursor cursor = mContactResolver.query(uriCommonContactInfo, null, null, null, null);

        recStartTime();
        int colIndexDisplayName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int conIndexContactId = cursor.getColumnIndex(ContactsContract.Contacts._ID);

        while (cursor.moveToNext()) {
            ContactRecord sc = new ContactRecord();

            String displayName = cursor.getString(colIndexDisplayName);
            String contactId = cursor.getString(conIndexContactId);

            sc.setId(contactId);
            sc.setDisplayName(displayName);

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = mContactResolver.query(uriPhoneContactInfo,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId},
                        null);

                int colIndexNumber = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int colIndexTypeId = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

                while (pCur.moveToNext()) {
                    String phone = pCur.getString(colIndexNumber);
                    String typeId = pCur.getString(colIndexTypeId);
                    String typeString = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(appContext.getResources(), Integer.parseInt(typeId), "");

                    sc.addPhone(typeString, phone);
                }

                pCur.close();
            }

            //sc.addPhone("null", "null");
            mData.add(sc);

            /*Cursor emailCursor = mContactResolver.query(uriEmailContactInfo,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);

            while (emailCursor.moveToNext()) {
                String phone = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String s = (String) ContactsContract.CommonDataKinds.Email.getTypeLabel(appContext.getResources(), type, "");

                //  Log.d(TAG, s + " email: " + phone);
            }

            emailCursor.close();*/

            //cursor.close();
        }

        cursor.close();
        showOperationTime("total time");

    }

    private void generateDummyData() {
        for (int i = 0; i < 15; i++) {
            ContactRecord cd = new ContactRecord();

            cd.setFirstName("first");
            cd.setLastName("last" + i);
            cd.setPhone(String.format("%d", 12345678 + i));
            cd.setLastContacted(new Date().toString());

            mData.add(cd);
        }
    }

    public ContactRecord getContact(String id) {
        for (ContactRecord cd : mData) {
            if (cd.getId().equals(id)) {
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

    public ArrayList<ContactRecord> getContacts() {
        return mData;
    }
}