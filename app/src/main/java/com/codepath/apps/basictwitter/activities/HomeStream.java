package com.codepath.apps.basictwitter.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
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
import com.codepath.apps.basictwitter.fragments.HomeStreamFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeStream extends FragmentActivity {
    private static final int COMPOSE_TWEET = 0;
    private TwitterRestClient client;
    private HomeStreamFragment homeStreamFragment;


    private User tweetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stream);
        client = RestClientApp.getRestClient();

        setupTweetUser();
        setupTabs();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeStreamFragment>(R.id.flContainer, this, "home",
                                HomeStreamFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }

    private void setupTweetUser() {
        client.getUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject json) {
                tweetUser = User.fromJSON(json);
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
            TweetsListFragment fragment = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.flContainer);
            fragment.insert(twVal, 0);

        } else if (resultCode == RESULT_CANCELED && requestCode == COMPOSE_TWEET) {

        }
    }

    public void onProfile(MenuItem item) {
        Intent i = new Intent(HomeStream.this, ProfileActivity.class);
        String userid = tweetUser.getStringUid();
        i.putExtra("userid", userid);
        i.putExtra("screenname", tweetUser.getScreenName());
        startActivity(i);
    }
}
