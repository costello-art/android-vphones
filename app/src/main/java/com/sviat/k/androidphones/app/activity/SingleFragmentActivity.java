package com.sviat.k.androidphones.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.sviat.k.androidphones.app.R;

/**
 * Created by Sviat on 04.11.14.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, createFragment(), "tagSingleFragmentActivity")
                .commit();
    }
}