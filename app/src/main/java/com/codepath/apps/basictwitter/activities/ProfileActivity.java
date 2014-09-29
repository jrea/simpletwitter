package com.codepath.apps.basictwitter.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.RestClientApp;
import com.codepath.apps.basictwitter.TwitterRestClient;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity {
    private TwitterRestClient client;
    private User tweetUser;
    private ImageView ivUserImage;
    private TextView tvName;
    private TextView tvTagline;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private String user_id;
    private String userScreenName;
    UserTimelineFragment fragmentTweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_id = getIntent().getStringExtra("userid");
        userScreenName = getIntent().getStringExtra("screenname");

        client = RestClientApp.getRestClient();
        ivUserImage = (ImageView) findViewById(R.id.iv_userimage);
        ivUserImage.setImageResource(android.R.color.transparent);
        tvName = (TextView) findViewById(R.id.name);
        tvTagline = (TextView) findViewById(R.id.tagline);
        tvFollowers = (TextView) findViewById(R.id.followers);
        tvFollowing = (TextView) findViewById(R.id.following);
        if (user_id != null && userScreenName != null) {
            setupExternalUser();
        } else {
            setupTweetUser();
        }

        fragmentTweets = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_userTimeline);
        fragmentTweets.setUsername(userScreenName);
        fragmentTweets.populateTimeline(0);

    }

    private void setupExternalUser() {
        client.getShowUser(user_id, userScreenName, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONObject json) {
                tweetUser = User.fromJSON(json);
                getActionBar().setTitle("@" + tweetUser.getScreenName());
                loadHeader(tweetUser);
            }
        });
    }

    private void setupTweetUser() {
        client.getUser(new JsonHttpResponseHandler(){
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONObject json) {
                tweetUser = User.fromJSON(json);
                getActionBar().setTitle("@" + tweetUser.getScreenName());
                loadHeader(tweetUser);
            }
        });
    }

    private void loadHeader(User tweetUser) {

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweetUser.getProfileImageUrl(), ivUserImage);
        tvName.setText(tweetUser.getName());
        tvTagline.setText(tweetUser.getTagline());
        tvFollowers.setText(tweetUser.getFollowers() + " followers");
        tvFollowing.setText(tweetUser.getFollowing() + " following");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
