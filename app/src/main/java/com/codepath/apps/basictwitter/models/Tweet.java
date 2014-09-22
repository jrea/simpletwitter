package com.codepath.apps.basictwitter.models;

import android.text.format.DateUtils;

import com.codepath.apps.basictwitter.TwitterRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.jar.JarOutputStream;

/**
 * Created by josephr on 9/20/14.
 */
public class Tweet implements Serializable{
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public static Tweet fromJSON(JSONObject obj) {
        Tweet tweet = new Tweet();
        //get the values from the json
        try {
            tweet.body = obj.getString("text");
            tweet.uid = obj.getLong("id");
            tweet.createdAt = obj.getString("created_at");
            tweet.user = User.fromJSON(obj.getJSONObject("user"));
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getTimeAgo() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(getCreatedAt()).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());

        for(int i = 0; i < json.length(); i++) {
            JSONObject tweetJson = null;
            try{
                tweetJson = json.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }

        }
        return tweets;
    }
}
