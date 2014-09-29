package com.codepath.apps.basictwitter.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.RestClientApp;
import com.codepath.apps.basictwitter.TwitterRestClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

/**
 * Created by josephr on 9/27/14.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private int page;
    public String username;
    private TwitterRestClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = RestClientApp.getRestClient();
        this.username = null;
        populateTimeline(0);
    }

    public void populateTimeline(int p) {
        page = p;
        client.getUserTimeline(page, this.username, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONArray json) {
                //get the username of the current user out of this query
                if(page == 0) {
                    clearAdapter();
                }
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
                super.onFailure(throwable, s);
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
