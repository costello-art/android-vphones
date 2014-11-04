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

    private ArrayList<ShortContactData> mData;
    private Context appContext;

    public ContactDatabase(Context appContext) {
        this.appContext = appContext;

        mData = new ArrayList<ShortContactData>();

        fetchContactDataAll();
    }

    private void fetchContactDataAll() {
        final Uri uriCommonContactInfo = ContactsContract.Contacts.CONTENT_URI;
        final Uri uriPhoneContactInfo = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        final Uri uriEmailContactInfo = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

        ContentResolver contactResolver = appContext.getContentResolver();

        long ts = System.currentTimeMillis();
        Cursor cursor = contactResolver.query(uriCommonContactInfo, null, null, null, null);
        long te = System.currentTimeMillis();

        System.out.println(String.format("pick time: %d ms", (te - ts)));

        int count = 0;
        while (cursor.moveToNext()) {

            ts = System.currentTimeMillis();
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            te = System.currentTimeMillis();

            System.out.println(String.format("contact info pick time: %d ms", (te - ts)));

            System.out.println(String.format("%s %s", contactId, displayName));

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                ts = System.currentTimeMillis();
                Cursor pCur = contactResolver.query(uriPhoneContactInfo,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId},
                        null);
                te = System.currentTimeMillis();

                System.out.println(String.format("contact phone info pick time: %d ms", (te - ts)));

                while (pCur.moveToNext()) {
                    String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String type = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    String s = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(appContext.getResources(), Integer.parseInt(type), "");


                    Log.d(TAG, s + " phone: " + phone);
                }

                pCur.close();

            }

            Cursor emailCursor = contactResolver.query(uriEmailContactInfo,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);

            while (emailCursor.moveToNext()) {
                String phone = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String s = (String) ContactsContract.CommonDataKinds.Email.getTypeLabel(appContext.getResources(), type, "");

                //  Log.d(TAG, s + " email: " + phone);
            }

            emailCursor.close();

            //cursor.close();
        }

        cursor.close();

        System.out.println("count = " + count);
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

    private void displayContacts() {

        ContentResolver cr = appContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        ShortContactData cd = new ShortContactData();

                        cd.setFirstName(name);

                        cd.setPhone(phoneNo);

                        mData.add(cd);

                        //    Log.d(TAG, "name" + name + "ph no" + phoneNo);
                        // Toast.makeText(this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
            }
        }
    }

    private void fetchContactData() {
        //ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        Uri uriBaseInfo = ContactsContract.Contacts.CONTENT_URI;
        Uri uriPhoneInfo = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] PROJECTION_BASE = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };

        String[] PROJECTION_NUMBER = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor baseInfo = appContext.getContentResolver().query(uriBaseInfo, PROJECTION_BASE, null, null, null);
        while (baseInfo.moveToNext()) {
            //String name = baseInfo.getString(baseInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //String phoneNumber = baseInfo.getString(baseInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String name = baseInfo.getString(baseInfo.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = baseInfo.getString(baseInfo.getColumnIndex(ContactsContract.Contacts._ID));

            Log.d(TAG, "id = " + id);

            //   String phoneSelection = "_ID = " + id;
            Cursor phoneNumberInfo = appContext.getContentResolver().query(uriPhoneInfo, PROJECTION_NUMBER, null, null, null);

            // String phoneNumber = phoneNumberInfo.getString(baseInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            // phoneNumberInfo.get
            ShortContactData cd = new ShortContactData();

            cd.setFirstName(name);
            //cd.setLastName("last" + i);
            //cd.setPhone(phoneNumber);
            //cd.setLastContacted(new Date().toString());

            mData.add(cd);
        }

        baseInfo.close();
    }

    public ShortContactData getContact(String id) {
        for (ShortContactData cd : mData) {
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

    public ArrayList<ShortContactData> getContacts() {
        return mData;
    }
}