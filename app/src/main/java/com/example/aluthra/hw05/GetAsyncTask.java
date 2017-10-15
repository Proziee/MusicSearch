package com.example.aluthra.hw05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by aluthra on 10/9/2017.
 */

public class GetAsyncTask extends AsyncTask<String, Integer, ArrayList<MusicTrack>>{
    Context context;
    OnTaskCompleted onTaskCompleted = null;

    public GetAsyncTask(Activity context){
        this.context = context;
        onTaskCompleted = (OnTaskCompleted) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<MusicTrack> musicTracks) {
        super.onPostExecute(musicTracks);
        onTaskCompleted.onTaskCompleted(musicTracks);
    }

    @Override
    protected ArrayList<MusicTrack> doInBackground(String... params) {
        ArrayList<MusicTrack> trackList;
        String json = readUrl(params[0]);
        if (params.length>1 && params[1].equals(TrackDetailsActivity.SIMILAR_TRACKS))
            trackList = similarTracks(json);
        else trackList =  searchTrack(json);
        return trackList;
    }


    private String readUrl(String link){
        HttpURLConnection con = null;
        BufferedReader bufferedReader = null;
        try{
            URL url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
            //con.connect();
            if (con.getResponseCode()== HttpURLConnection.HTTP_OK){
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line ="";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!=null)
                    stringBuilder.append(line);
                return stringBuilder.toString();

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (con!=null)
                con.disconnect();

            if (bufferedReader!=null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    private ArrayList<MusicTrack> searchTrack(String json){
        ArrayList<MusicTrack> trackList = new ArrayList<MusicTrack>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject results = root.getJSONObject("results");
            JSONObject trackMatches = results.getJSONObject("trackmatches");
            JSONArray jsonArray = trackMatches.getJSONArray("track");
            for (int i=0; i < jsonArray.length(); i ++) {
                JSONObject trackObject = jsonArray.getJSONObject(i);
                String name = trackObject.getString("name");
                String artist = trackObject.getString("artist");
                String url = trackObject.getString("url");
                String smallImageUrl = null, largeImageUrl = null;
                JSONArray imageArray = trackObject.getJSONArray("image");
                for (int j=0; j<imageArray.length();j++){
                    JSONObject imageObject = imageArray.getJSONObject(j);
                    String size = imageObject.getString("size");
                    if (size.equals("small")){
                        smallImageUrl = imageObject.getString("#text");
                    }else if (size.equals("large")){
                        largeImageUrl = imageObject.getString("#text");
                    }
                }
                MusicTrack mtrack = new MusicTrack(name, artist, url, smallImageUrl, largeImageUrl );
                trackList.add(mtrack);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackList;
    }


    private ArrayList<MusicTrack> similarTracks(String json){
        ArrayList<MusicTrack> trackList = new ArrayList<MusicTrack>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject similartracks = root.getJSONObject("similartracks");
            JSONArray jsonArray = similartracks.getJSONArray("track");
            for (int i=0; i < jsonArray.length(); i ++) {
                JSONObject trackObject = jsonArray.getJSONObject(i);
                String name = trackObject.getString("name");
                JSONObject artistObject = trackObject.getJSONObject("artist");
                String artist = artistObject.getString("name");
                String url = trackObject.getString("url");
                String smallImageUrl = null, largeImageUrl = null;
                JSONArray imageArray = trackObject.getJSONArray("image");
                for (int j=0; j<imageArray.length();j++){
                    JSONObject imageObject = imageArray.getJSONObject(j);
                    String size = imageObject.getString("size");
                    if (size.equals("small")){
                        smallImageUrl = imageObject.getString("#text");
                    }else if (size.equals("large")){
                        largeImageUrl = imageObject.getString("#text");
                    }
                }
                MusicTrack mtrack = new MusicTrack(name, artist, url, smallImageUrl, largeImageUrl );
                trackList.add(mtrack);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackList;
    }

}


