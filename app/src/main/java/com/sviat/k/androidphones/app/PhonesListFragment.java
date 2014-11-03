package com.sviat.k.androidphones.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sviat.k.androidphones.app.contacts.ContactDatabase;
import com.sviat.k.androidphones.app.contacts.ShortContactData;

import java.util.ArrayList;

/**
 * Created by Sviat on 04.11.14.
 */
public class PhonesListFragment extends ListFragment {

    private static final String TAG = "PhonesListFragment";
    private ArrayList<ShortContactData> mContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setTitle(R.string.crime_title);
        mContacts = ContactDatabase.get(getActivity()).getContacts();

        CrimeAdapter adapter = new CrimeAdapter(mContacts);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

/*        Crime c = (Crime) getListAdapter().getItem(position);
        Intent intentCrimeActivity = new Intent(getActivity(), CrimeActivity.class);

        intentCrimeActivity.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(intentCrimeActivity);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class CrimeAdapter extends ArrayAdapter<ShortContactData> {
        public CrimeAdapter(ArrayList<ShortContactData> contacts) {
            super(getActivity(), 0, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_contact, null);
            }

            ShortContactData cd = (ShortContactData) getListAdapter().getItem(position);

            TextView contactName = (TextView) convertView.findViewById(R.id.contact_Name);
            TextView contactPhoneNumber = (TextView) convertView.findViewById(R.id.contact_Phone);
            TextView contactLastCallTime = (TextView) convertView.findViewById(R.id.contact_LastCallTime);

            contactName.setText(cd.getFullName());
            contactPhoneNumber.setText(cd.getPhoneNumber());
            contactLastCallTime.setText(cd.getLastCall());

            return convertView;
        }
    }
}
