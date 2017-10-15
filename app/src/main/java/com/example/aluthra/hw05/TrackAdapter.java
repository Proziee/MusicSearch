package com.example.aluthra.hw05;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by aluthra on 10/10/2017.
 */

public class TrackAdapter extends ArrayAdapter<MusicTrack> {

    Context context;
    ArrayList<MusicTrack> musicTracks;

    public TrackAdapter(Activity context, ArrayList<MusicTrack> musicTracks) {
        super(context, R.layout.track_row_layout,musicTracks);
        this.context = context;
        this.musicTracks = musicTracks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View trackRowView = layoutInflater.inflate(R.layout.track_row_layout, parent, false);
        TextView trackname = (TextView) trackRowView.findViewById(R.id.textViewTrack);
        TextView artist = (TextView) trackRowView.findViewById(R.id.textViewArtist);
        ImageView trackImage = (ImageView) trackRowView.findViewById(R.id.imageViewTrack);
        trackname.setText(musicTracks.get(position).getName());
        artist.setText(musicTracks.get(position).getArtist());
        Picasso.with(context).load(musicTracks.get(position).getSmallImageUrl()).into(trackImage);
        final ImageView starImage = (ImageView)trackRowView.findViewById(R.id.imageViewStar);
        starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ((ImageView)v).getDrawable().getConstantState().equals(trackRowView.getResources().getDrawable(android.R.drawable.btn_star_big_off).getConstantState()))
                    starImage.setBackground(trackRowView.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                else if (((ImageView)v).getDrawable().getConstantState().equals(trackRowView.getResources().getDrawable(android.R.drawable.btn_star_big_on).getConstantState()))
                    starImage.setBackground(trackRowView.getResources().getDrawable(android.R.drawable.btn_star_big_off));
            }
        });
        return trackRowView;
    }
}
