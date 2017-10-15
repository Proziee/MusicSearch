package com.example.aluthra.hw05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    static final String DETAIL_TRACK = "detailTrack";
    static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?format=json&method=track.search&api_key=7990ee210e684f02ef1626fff82db340&limit=20&track=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        final ArrayList<MusicTrack> musicTracks = (ArrayList<MusicTrack>) getIntent().getExtras().get(MainActivity.TRACKLIST_KEY);
        TrackAdapter trackAdapter = new TrackAdapter(this, musicTracks);
        //ArrayAdapter<MusicTrack> arrayAdapter = new ArrayAdapter<MusicTrack>(this, android.R.layout.simple_list_item_1, android.R.id.text1, musicTracks);
        ListView listViewTracks = (ListView) findViewById(R.id.listViewTracks);
        listViewTracks.setAdapter(trackAdapter);
        trackAdapter.setNotifyOnChange(true);
        listViewTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultsActivity.this, TrackDetailsActivity.class);
                intent.putExtra(DETAIL_TRACK,musicTracks.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
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
                finish();
                return true;
            case R.id.action_quit: this.finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
