package com.example.aluthra.hw05;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.aluthra.hw05.SearchResultsActivity.DETAIL_TRACK;

public class TrackDetailsActivity extends AppCompatActivity implements OnTaskCompleted {

    MusicTrack detailTrack;
    TextView textViewUrl;
    static final String SIMILAR_TRACKS = "similarTracks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        detailTrack = (MusicTrack) getIntent().getExtras().get(DETAIL_TRACK);
        ((TextView)findViewById(R.id.textViewNameValue)).setText(detailTrack.getName());
        ((TextView)findViewById(R.id.textViewArtistValue)).setText(detailTrack.getArtist());
        textViewUrl = (TextView)findViewById(R.id.textViewUrlValue);
        SpannableString content = new SpannableString(detailTrack.getUrl());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewUrl.setText(content);
        textViewUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(textViewUrl.getText().toString());
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
        Picasso.with(TrackDetailsActivity.this).load(detailTrack.getLargeImageUrl()).into((ImageView)findViewById(R.id.imageViewDisplayTrack));
        String similarTracksUrl = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist="+detailTrack.getArtist() +"&track="+detailTrack.getName()
                +"&api_key=7990ee210e684f02ef1626fff82db340&format=json&limit=10";
        new GetAsyncTask(this).execute(similarTracksUrl, SIMILAR_TRACKS);
        //ArrayList<MusicTrack> musicTracks = (ArrayList<MusicTrack>) getIntent().getExtras().get(MainActivity.TRACKLIST_KEY);
        ArrayList<MusicTrack> musicTracks = (ArrayList<MusicTrack>) new ArrayList<MusicTrack>();
        TrackAdapter trackAdapter = new TrackAdapter(this, musicTracks);
        //ArrayAdapter<MusicTrack> arrayAdapter = new ArrayAdapter<MusicTrack>(this, android.R.layout.simple_list_item_1, android.R.id.text1, musicTracks);
        ListView listViewTracks = (ListView) findViewById(R.id.listViewSimilarTracks);
        listViewTracks.setAdapter(trackAdapter);
        trackAdapter.setNotifyOnChange(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskCompleted(final ArrayList<MusicTrack> trackList) {
        TrackAdapter trackAdapter = new TrackAdapter(this, trackList);
        ListView listViewTracks = (ListView) findViewById(R.id.listViewSimilarTracks);
        listViewTracks.setAdapter(trackAdapter);
        trackAdapter.setNotifyOnChange(true);
        listViewTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrackDetailsActivity.this, TrackDetailsActivity.class);
                intent.putExtra(DETAIL_TRACK,trackList.get(position));
                startActivity(intent);
                finish();
            }
        });
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
