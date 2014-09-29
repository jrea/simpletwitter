package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.listeners.ProfileImageClickListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by josephr on 9/20/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    Context mContext;
    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0 , tweets);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView iv_profile = (ImageView) v.findViewById(R.id.iv_profileImg);
        iv_profile.setImageResource(android.R.color.transparent);



        TextView tv_username = (TextView) v.findViewById(R.id.tv_compose_username);
        TextView tv_screenname = (TextView) v.findViewById(R.id.tv_screenname);
        TextView tv_body = (TextView) v.findViewById(R.id.tv_body);
        TextView tv_timeago = (TextView) v.findViewById(R.id.tv_timeAgo);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), iv_profile);
        iv_profile.setOnClickListener(new ProfileImageClickListener(mContext, tweet.getUser()));

        tv_screenname.setText(tweet.getUser().getName());
        tv_username.setText("@" + tweet.getUser().getScreenName());
        tv_body.setText(tweet.getBody());
        tv_timeago.setText(tweet.getTimeAgo());

        return v;
    }
}
