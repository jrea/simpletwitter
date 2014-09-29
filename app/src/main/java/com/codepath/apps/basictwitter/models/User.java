package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by josephr on 9/20/14.
 */
public class User implements Serializable{
    private String name, screenName, profileImageUrl, tagline;
    private int followers, following;
    private long uid;
    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.tagline = json.getString("description");
            u.followers = json.getInt("followers_count");
            u.following = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return u;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getUid() {
        return uid;
    }

    public String getTagline() {
        return tagline;
    }


    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getStringUid() { return String.valueOf(uid);}

}
