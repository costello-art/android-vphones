package com.sviat.k.androidphones.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.TextView;

/**
 * Created by Sviat on 29.10.14.
 */
public class ShowReceivedMessage extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_received_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(PhonesMain.EXTRA_MESSAGE_TO_RECEIVE);

        TextView textView = (TextView) findViewById(R.id.textViewReceivedMessage);
        textView.setText(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}