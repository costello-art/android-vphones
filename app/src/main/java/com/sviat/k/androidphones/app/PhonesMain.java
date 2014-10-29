package com.sviat.k.androidphones.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class PhonesMain extends ActionBarActivity {

    public static final String EXTRA_MESSAGE_TO_RECEIVE = "com.sviat.k.androidphones.app.message_to_send";

    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    private static final int[] TO_IDS = {R.id.text1};
    private ListView listContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_main);

        listContacts = (ListView) findViewById(R.id.listViewContacts);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phones_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Intent searchDialog = new Intent(this, com.sviat.k.androidphones.app.searchDialog.class);
        startActivity(searchDialog);

    }
}