package com.sviat.k.androidphones.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.sviat.k.androidphones.app.ContactDetailFragment;
import com.sviat.k.androidphones.app.ContactListFragment;
import com.sviat.k.androidphones.app.R;
import com.sviat.k.androidphones.app.activity.SingleFragmentActivity;

import java.util.UUID;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String contactId = getIntent().getStringExtra(ContactDetailFragment.EXTRA_CONTACT_ID);

        return ContactDetailFragment.newInstance(contactId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, createFragment(), "tagContactDetailActivity").commit();
    }
}