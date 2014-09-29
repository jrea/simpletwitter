package com.codepath.apps.basictwitter.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.RestClientApp;
import com.codepath.apps.basictwitter.TwitterRestClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeStreamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeStreamFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HomeStreamFragment extends TweetsListFragment {
    private int page;
    private TwitterRestClient client;

    public HomeStreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = RestClientApp.getRestClient();
        populateTimeline(0);
    }

    public void populateTimeline(int p) {
        page = p;
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONArray json) {
                //get the username of the current user out of this query
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

}
