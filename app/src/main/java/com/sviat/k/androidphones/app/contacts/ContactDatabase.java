package com.sviat.k.androidphones.app.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDatabase {
    private static final String TAG = "ContactDatabase";
    private static ContactDatabase sContactDatabase;

    private final Uri uriCommonContactInfo = Contacts.CONTENT_URI;
    private final Uri uriPhoneContactInfo = Phone.CONTENT_URI;
    //private final Uri uriEmailContactInfo = CommonDataKinds.Email.CONTENT_URI;

    private Context appContext;
    private ContentResolver mContactResolver;

    private HashMap<String, ContactRecord> mData;

    private long timeStart;
    private long timeEnd;

    private ContactDatabase(Context appContext) {
        this.appContext = appContext;

        mData = new HashMap<String, ContactRecord>();
        mContactResolver = appContext.getContentResolver();
    }

    private void recStartTime() {
        timeStart = System.currentTimeMillis();
    }

    private void showOperationTime(String title) {
        timeEnd = System.currentTimeMillis();

        long time = (timeEnd - timeStart);
        Log.d(TAG, String.format("Operation \'%s\' took %d ms", title, time));
    }

    /**
     * Fetch all available phones and its types for given contact ID
     *
     * @param id an contact id from DB
     */
    private void fetchPhones(String id) {
        recStartTime();
        Cursor pCur = mContactResolver.query(uriPhoneContactInfo,
                new String[]{Phone._ID, Phone.TYPE, Phone.NUMBER},
                Phone.CONTACT_ID + " = ?",
                new String[]{id},
                null);

        int colIndexNumber = pCur.getColumnIndex(Phone.NUMBER);
        int colIndexTypeId = pCur.getColumnIndex(Phone.TYPE);

        while (pCur.moveToNext()) {
            String phone = pCur.getString(colIndexNumber);
            String typeId = pCur.getString(colIndexTypeId);
            String typeString = (String) Phone.getTypeLabel(appContext.getResources(), Integer.parseInt(typeId), "");

            mData.get(id).addPhone(typeString, phone);
        }

        pCur.close();
        showOperationTime("fetch phone for " + id);
    }

    private void fetchBasicContactInfo() {
        Cursor cursor = mContactResolver.query(
                uriCommonContactInfo,
                new String[]{Contacts._ID, Contacts.DISPLAY_NAME, Contacts.LAST_TIME_CONTACTED},
                "HAS_PHONE_NUMBER = ?",
                new String[]{"1"},
                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        recStartTime();
        int conIndexContactId = cursor.getColumnIndex(Contacts._ID);
        int colIndexDisplayName = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
        int colIndexLastCall = cursor.getColumnIndex(Contacts.LAST_TIME_CONTACTED);


        while (cursor.moveToNext()) {
            ContactRecord contactRecord = new ContactRecord();

            String contactId = cursor.getString(conIndexContactId);
            String displayName = cursor.getString(colIndexDisplayName);
            String lastCall = cursor.getString(colIndexLastCall);

            contactRecord.setId(contactId);
            contactRecord.setDisplayName(displayName);
            contactRecord.setLastContacted(lastCall);

            mData.put(contactId, contactRecord);
        }

        cursor.close();
        showOperationTime("total time");
    }

    private void fetchEmails(String id) {
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

    private void generateDummyData() {
        for (int i = 0; i < 15; i++) {
            ContactRecord cd = new ContactRecord();

            cd.setDisplayName("name#" + i);
            cd.setLastContacted(new Date().toString());

            mData.put(String.valueOf(i), cd);
        }
    }

    public ContactRecord getContact(String id) {
        return mData.get(id);
    }

    public static ContactDatabase get(Context c) {
        if (sContactDatabase == null) {
            sContactDatabase = new ContactDatabase(c.getApplicationContext());
        }

        return sContactDatabase;
    }

    public ArrayList<ContactPhoneRecord> requestPhones(String contactId) {
        if (mData.get(contactId).getPhones() != null) {
            Log.d(TAG, String.format("Phones for contact id %s not null. Giving back cached data.", contactId));
            return mData.get(contactId).getPhones();
        }

        Log.d(TAG, "Phones for contact id=%s is null. Going to fetch it and return");
        fetchPhones(contactId);
        return mData.get(contactId).getPhones();
    }

    /**
     * Get list of all contacts. Deprecated.
     *
     * @return list of all contacts
     */
    @Deprecated
    public ArrayList<ContactRecord> getContacts() {
        fetchBasicContactInfo();
        ArrayList<ContactRecord> list = new ArrayList<ContactRecord>();

        for (String id : mData.keySet()) {
            list.add(mData.get(id));
        }

        return list;
    }
}