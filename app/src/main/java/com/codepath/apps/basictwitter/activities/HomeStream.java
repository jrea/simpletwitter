package com.codepath.apps.basictwitter.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.RestClientApp;
import com.codepath.apps.basictwitter.TwitterRestClient;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeStream extends Activity {
    private static final int COMPOSE_TWEET = 0;
    private TwitterRestClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    private int page;
    private User tweetUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stream);
        client = RestClientApp.getRestClient();
        populateTimeline(0);
        lvTweets = (ListView) findViewById(R.id.lv_tweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            populateTimeline(page);
            }
        });
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        setupTweetUser();
    }

    private void setupTweetUser() {
        client.getUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject json) {
                tweetUser = User.fromJSON(json);
            }
        });
    }

    public void populateTimeline(int p) {
        page = p;
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONArray json) {
                //get the username of the current user out of this query
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
                super.onFailure(throwable, s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_stream, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_compose:
                createTweet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createTweet() {
        Intent i = new Intent(HomeStream.this, ComposeTweetActivity.class);
        startActivityForResult(i, COMPOSE_TWEET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == COMPOSE_TWEET) {
            Tweet twVal = (Tweet) data.getExtras().getSerializable("tweet");
            aTweets.insert(twVal, 0);

        } else if (resultCode == RESULT_CANCELED && requestCode == COMPOSE_TWEET) {

        }
    }
}
