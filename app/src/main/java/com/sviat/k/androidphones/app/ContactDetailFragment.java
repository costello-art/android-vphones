package com.sviat.k.androidphones.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sviat.k.androidphones.app.contacts.ContactDatabase;
import com.sviat.k.androidphones.app.contacts.ContactPhoneRecord;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDetailFragment extends Fragment {
    public static final String EXTRA_CONTACT_ID = "com.sviat.k.androidphones.app.contact_id";
    private ContactDatabase contactDatabase;
    private String contactId;

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
        TextView textPhones = (TextView) v.findViewById(R.id.textViewContactPhone);
        TextView textLastCall = (TextView) v.findViewById(R.id.textViewContactLastCall);

        contactName.setText(contactDatabase.getContact(contactId).getDisplayName());

        String phones = "";

        for (ContactPhoneRecord phone : contactDatabase.requestPhones(contactId)) {
            phones += String.format("%s: %s\n", phone.getType(), phone.getPhone());
        }

        textPhones.setText(phones);

        textLastCall.setText(contactDatabase.getContact(contactId).getLastCall());

        return v;
    }
}