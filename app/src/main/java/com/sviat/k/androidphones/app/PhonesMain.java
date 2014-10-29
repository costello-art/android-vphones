package com.sviat.k.androidphones.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class PhonesMain extends ActionBarActivity {

    public static final String EXTRA_MESSAGE_TO_RECEIVE = "com.sviat.k.androidphones.app.message_to_send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_main);
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

            case R.id.action_settings:
                openSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {

    }

    private void openSearch() {
        Intent searchDialog = new Intent(this, com.sviat.k.androidphones.app.searchDialog.class);
        startActivity(searchDialog);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ShowReceivedMessage.class);
        EditText textToSend = (EditText) findViewById(R.id.editText_message);
        String message = textToSend.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_TO_RECEIVE, message);

        startActivity(intent);
    }
}
