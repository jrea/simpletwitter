package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterRestClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "cKqj2kHPMaQXAVihu1ZlIfmXe";       // Change this
	public static final String REST_CONSUMER_SECRET = "V19VMC85jAXGsKzwS6jfgGdpoFgQhGc8VHXVHxjiw0tEEICzsb"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterRestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getHomeTimeline(int page, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        client.get(apiUrl, params, handler);
    }

    public void postUpdate(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);
    }

    public void getUser(JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        params.put("include_entities", "false");
        params.put("skip_status", "true");
        client.get(apiUrl, params, handler);
    }

    public void getShowUser(String user_id, String screen_name, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("screen_name", screen_name);
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(int page, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(JsonHttpResponseHandler handler) {
        String url = getApiUrl("statuses/user_timeline.json");
        client.get(url, null, handler);
    }

    public void getUserTimeline(int page, String screen_name, JsonHttpResponseHandler handler) {
        String url = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        params.put("screen_name", screen_name);
        client.get(url, params, handler);
    }
}