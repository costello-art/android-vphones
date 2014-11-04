package com.sviat.k.androidphones.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactDetail extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        //UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return ContactDetailFragment.newInstance();
       // return CrimeFragment.newInstance(crimeId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, createFragment(), "tagContactDetail").commit();
    }
}
