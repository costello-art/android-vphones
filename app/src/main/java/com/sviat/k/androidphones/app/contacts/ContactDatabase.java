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

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDatabase {
    private static final String TAG = "ContactDatabase";
    private static ContactDatabase sContactDatabase;

    private final Uri uriCommonContactInfo = Contacts.CONTENT_URI;
    private final Uri uriPhoneContactInfo = Phone.CONTENT_URI;
    //private final Uri uriEmailContactInfo = CommonDataKinds.Email.CONTENT_URI;

    private ArrayList<ContactRecord> mData;
    private Context appContext;
    private ContentResolver mContactResolver;

    private long timeStart;
    private long timeEnd;

    private ContactDatabase(Context appContext) {
        this.appContext = appContext;

        mData = new ArrayList<ContactRecord>();
        mContactResolver = appContext.getContentResolver();

        fetchContact();
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
     * TODO: fetch only if (Integer.parseInt(cursor.getString(colIndexHasPhoneNumber)) > 0)
     *
     * @param id an contact id from DB
     */
    private void fetchPhones(String id) {
        //int colIndexHasPhoneNumber = cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER);

        int recId = -1;
        for (ContactRecord cr : mData) {
            if (cr.getId().equals(id)) {
                recId = mData.indexOf(cr);
            }
        }

        String[] projectionPhone = new String[]{
                Phone._ID,
                Phone.TYPE,
                Phone.NUMBER
        };

        Cursor pCur = mContactResolver.query(uriPhoneContactInfo,
                projectionPhone,
                Phone.CONTACT_ID + " = ?", new String[]{id},
                null);

        int colIndexNumber = pCur.getColumnIndex(Phone.NUMBER);
        int colIndexTypeId = pCur.getColumnIndex(Phone.TYPE);

        while (pCur.moveToNext()) {
            String phone = pCur.getString(colIndexNumber);
            String typeId = pCur.getString(colIndexTypeId);
            String typeString = (String) Phone.getTypeLabel(appContext.getResources(), Integer.parseInt(typeId), "");

            mData.get(recId).addPhone(typeString, phone);
        }

        pCur.close();
    }

    private void fetchContact() {
        Cursor cursor = mContactResolver.query(uriCommonContactInfo, null, null, null, null);

        recStartTime();
        int conIndexContactId = cursor.getColumnIndex(Contacts._ID);
        int colIndexDisplayName = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
        int colIndexLastCall = cursor.getColumnIndex(Contacts.LAST_TIME_CONTACTED);


        while (cursor.moveToNext()) {
            ContactRecord sc = new ContactRecord();

            String contactId = cursor.getString(conIndexContactId);
            String displayName = cursor.getString(colIndexDisplayName);
            String lastCall = cursor.getString(colIndexLastCall);

            sc.setId(contactId);
            sc.setDisplayName(displayName);
            sc.setLastContacted(lastCall);

            mData.add(sc);
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