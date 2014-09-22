package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.apps.basictwitter.RestClientApp;
import com.codepath.apps.basictwitter.TwitterRestClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ComposeTweetActivity extends Activity {
    private TwitterRestClient client;
    private EditText statusText;
    private Tweet t;
    private ImageView iv_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        client = RestClientApp.getRestClient();
        statusText = (EditText) findViewById(R.id.ev_statusText);

    }

    public void composeTweet(View v) {
        client.postUpdate(statusText.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject jsonTweet) {
                Intent i = new Intent();
                t = Tweet.fromJSON(jsonTweet);
                i.putExtra("tweet", t);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_tweet, menu);
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
