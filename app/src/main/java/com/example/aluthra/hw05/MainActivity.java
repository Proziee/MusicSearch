package com.example.aluthra.hw05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted{


    static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?format=json&method=track.search&api_key=7990ee210e684f02ef1626fff82db340&limit=20&track=";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    static final String TRACKLIST_KEY = "trackListKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextSearch = (EditText) findViewById(R.id.editTextSearch);
                //OnTaskCompleted onTaskCompleted;
                new GetAsyncTask(MainActivity.this).execute(MainActivity.BASE_URL+editTextSearch.getText().toString());
            }
        });

    }

    @Override
    public void onTaskCompleted(ArrayList<MusicTrack> trackList) {
        Intent intent = new Intent(MainActivity.this,SearchResultsActivity.class);
        intent.putExtra(TRACKLIST_KEY,trackList);
        startActivity(intent);
    }


    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_home: Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finishAffinity();
                return true;
            case R.id.action_quit: this.finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
