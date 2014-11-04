package com.sviat.k.androidphones.app.activity;

import android.support.v4.app.Fragment;
import com.sviat.k.androidphones.app.PhonesListFragment;
import com.sviat.k.androidphones.app.activity.SingleFragmentActivity;

/**
 * Created by Sviat on 04.11.14.
 */
public class PhoneListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PhonesListFragment();
    }
}
