package com.sviat.k.androidphones.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sviat.k.androidphones.app.activity.ContactDetailActivity;
import com.sviat.k.androidphones.app.contacts.ContactDatabase;
import com.sviat.k.androidphones.app.contacts.ContactRecord;

import java.util.ArrayList;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactListFragment extends ListFragment {
    private static final String TAG = "ContactListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetContactList().execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ContactRecord cr = (ContactRecord) getListAdapter().getItem(position);
        Intent intentContactDetails = new Intent(getActivity(), ContactDetailActivity.class);

        intentContactDetails.putExtra(ContactDetailFragment.EXTRA_CONTACT_ID, cr.getId());
        startActivity(intentContactDetails);
    }

    private class GetContactList extends AsyncTask<Void, String, ArrayList<ContactRecord>> {
        @Override
        protected ArrayList<ContactRecord> doInBackground(Void... params) {
            return ContactDatabase.get(getActivity()).getContacts();
        }

        @Override
        protected void onPostExecute(ArrayList<ContactRecord> contactRecords) {
            super.onPostExecute(contactRecords);
            ContactListAdapter adapter = new ContactListAdapter(contactRecords);
            setListAdapter(adapter);
        }
    }

    private class ContactListAdapter extends ArrayAdapter<ContactRecord> {
        public ContactListAdapter(ArrayList<ContactRecord> contacts) {
            super(getActivity(), 0, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_contact, null);
            }

            ContactRecord cd = (ContactRecord) getListAdapter().getItem(position);

            TextView contactName = (TextView) convertView.findViewById(R.id.contact_Name);
            TextView contactLastCallTime = (TextView) convertView.findViewById(R.id.contact_LastCallTime);

            contactName.setText(cd.getDisplayName());
            contactLastCallTime.setText(cd.getLastCall());

            return convertView;
        }
    }
}