package com.codepath.apps.basictwitter.fragments;



import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public abstract class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    public ListView lvTweets;
    private int page;

    public TweetsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lv_tweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
            }
        });
        lvTweets.setAdapter(aTweets);

        return v;
    }

    public abstract void populateTimeline(int page);

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }
    public void clearAdapter() {
        aTweets.clear();
    }
    public void insert(Tweet twVal, int i) {
        aTweets.insert(twVal, i);
    }
}
