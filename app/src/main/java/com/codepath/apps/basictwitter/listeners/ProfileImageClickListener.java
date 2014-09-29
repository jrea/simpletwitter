package com.codepath.apps.basictwitter.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.User;

/**
 * Created by josephr on 9/28/14.
 */
public class ProfileImageClickListener implements View.OnClickListener {
    User user;
    Context context;
    TweetArrayAdapter adapter;


    public ProfileImageClickListener(Context c, User u) {
        user = u;
        context = c;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(context, ProfileActivity.class);
        String userid = user.getStringUid();
        i.putExtra("userid", userid);
        i.putExtra("screenname", user.getScreenName());
        context.startActivity(i);




    }
}