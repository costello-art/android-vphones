package com.sviat.k.androidphones.app;

import android.net.Uri;

/**
 * Created by Sviat on 30.10.14.
 */
public interface OnContactsIterationListener {
    public void onContactSelected(Uri contactUri);
    public void onSelectionCleared();
}
