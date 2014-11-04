package com.sviat.k.androidphones.app.activity;

import android.support.v4.app.Fragment;
import com.sviat.k.androidphones.app.ContactListFragment;

/**
 * Created by Sviat on 04.11.14.
 */
public class ContactListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }
}
