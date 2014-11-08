package com.sviat.k.androidphones.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sviat.k.androidphones.app.contacts.ContactDatabase;
import com.sviat.k.androidphones.app.contacts.ContactEmailRecord;
import com.sviat.k.androidphones.app.contacts.ContactPhoneRecord;

import java.util.ArrayList;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDetailFragment extends Fragment {
    public static final String EXTRA_CONTACT_ID = "com.sviat.k.androidphones.app.contact_id";
    private ContactDatabase contactDatabase;
    private String contactId;
    private TextView textPhones;
    private TextView textEmails;

    public static Fragment newInstance(String contactId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_CONTACT_ID, contactId);
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactId = (String) getArguments().getSerializable(EXTRA_CONTACT_ID);

        contactDatabase = ContactDatabase.get(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_info, container, false);

        TextView contactName = (TextView) v.findViewById(R.id.textViewContactName);
        TextView textLastCall = (TextView) v.findViewById(R.id.textViewContactLastCall);
        textPhones = (TextView) v.findViewById(R.id.textViewContactPhone);
        textEmails = (TextView) v.findViewById(R.id.textViewContactEmail);

        contactName.setText(contactDatabase.getContact(contactId).getDisplayName());
        textLastCall.setText(contactDatabase.getContact(contactId).getLastCall());

        new FetchPhoneAndEmails().execute();

        return v;
    }

    private class FetchPhoneAndEmails extends AsyncTask<Void, Void, Void> {

        private ArrayList<ContactEmailRecord> contactEmailRecords;
        private ArrayList<ContactPhoneRecord> contactPhoneRecords;

        @Override
        protected Void doInBackground(Void... params) {
            contactPhoneRecords = contactDatabase.requestPhones(contactId);
            contactEmailRecords = contactDatabase.requestEmails(contactId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (contactPhoneRecords == null) {
                textPhones.setText("no data");
            } else {
                StringBuilder phones = new StringBuilder();

                for (ContactPhoneRecord phone : contactPhoneRecords) {
                    phones.append(String.format("%s: %s\n", phone.getType(), phone.getPhone()));
                }

                textPhones.setText(phones);
            }

            if (contactEmailRecords == null) {
                textEmails.setText("no data");
            } else {
                StringBuilder emails = new StringBuilder();
                for (ContactEmailRecord email : contactEmailRecords) {
                    emails.append(String.format("%s: %s\n", email.getType(), email.getEmail()));
                }

                textEmails.setText(emails);
            }

        }
    }
}
